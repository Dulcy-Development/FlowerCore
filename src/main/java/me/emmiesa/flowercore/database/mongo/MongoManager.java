package me.emmiesa.flowercore.database.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.playersettings.PlayerSettings;
import me.emmiesa.flowercore.profile.Profile;
import me.emmiesa.flowercore.punishments.PunishmentSerializer;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by lrxh_
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

@Getter
public class MongoManager {

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    public void initializeMongo() {
        String URI = FlowerCore.getInstance().getConfig("database.yml").getString("database.uri");
        this.mongoClient = MongoClients.create(URI);

        MongoDatabase database = getMongoClient().getDatabase(FlowerCore.getInstance()
                .getConfig("database.yml")
                .getString("database.database-name"));

        String collectionName = "profile";

        if (!collectionExists(database, collectionName)) {
            database.createCollection(collectionName);
        }

        this.collection = database.getCollection(collectionName);
    }

    /**
     * Initialize a profile for a player
     *
     * @param playerUUID the UUID of the player
     */
    public void initializeProfile(UUID playerUUID) {
        Document doc = getCollection().find(eq("UUID", playerUUID.toString())).first();
        Profile profile;

        if (doc == null) {
            PlayerSettings defaultSettings = new PlayerSettings(true, true, true);
            profile = createDefaultProfile(playerUUID, defaultSettings);
            saveProfile(profile);
        } else {
            Document optionDoc = doc.get("option", Document.class);
            boolean privateMessagesEnabled = optionDoc.getBoolean("privateMessagesEnabled", true);
            boolean soundsEnabled = optionDoc.getBoolean("messageSoundsEnabled", true);
            boolean globalChatEnabled = optionDoc.getBoolean("globalChatEnabled", true);

            // (global, private, sounds) needs to be in the correct order as above because otherwise it will set the wrong settings to false or true
            PlayerSettings playerSettings = new PlayerSettings(globalChatEnabled, privateMessagesEnabled, soundsEnabled);
            profile = createProfile(playerUUID, doc, playerSettings);
        }
        FlowerCore.getInstance().getPlayerManager().addRank(profile);
    }

    /**
     * Create a default profile for a player
     *
     * @param playerUUID the UUID of the player
     * @param playerSettings the player settings
     * @return the profile
     */
    private Profile createDefaultProfile(UUID playerUUID, PlayerSettings playerSettings) {
        return Profile.builder()
                .uuid(playerUUID)
                .rank(FlowerCore.getInstance().getRanksManager().getDefaultRank())
                .tag(null)
                .playerSettings(playerSettings)
                .build();
    }

    /**
     * Create a profile from a document
     *
     * @param playerUUID the UUID of the player
     * @param doc the document
     * @param playerSettings the player settings
     * @return the profile
     */
    private Profile createProfile(UUID playerUUID, Document doc, PlayerSettings playerSettings) {
        return Profile.builder()
                .uuid(playerUUID)
                .rank(FlowerCore.getInstance().getRanksManager().getRank(doc.getString("rank")))
                .punishments(PunishmentSerializer.deserialize(doc.getList("punishments", String.class)))
                .playerSettings(playerSettings)
                .tag(FlowerCore.getInstance().getTagsManager().getTag(doc.getString("tag")))
                .build();
    }

    public void saveAllPlayerData() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            saveProfile(player.getUniqueId());
        }
    }

    /**
     * Save a profile to the database
     *
     * @param profile the profile to save
     */
    public void saveProfile(Profile profile) {
        UUID playerUUID = profile.getUuid();

        if (playerUUID != null) {
            Document profileDoc = createDocument(playerUUID, profile);
            getCollection().replaceOne(eq("UUID", playerUUID.toString()), profileDoc, new ReplaceOptions().upsert(true));
        }
    }

    /**
     * Save a profile to the database
     *
     * @param playerUUID the UUID of the player
     */
    public void saveProfile(UUID playerUUID) {
        Profile profile = FlowerCore.getInstance().getPlayerManager().getProfiles().get(playerUUID);

        if (profile != null) {
            Document profileDoc = createDocument(playerUUID, profile);
            getCollection().replaceOne(eq("UUID", playerUUID.toString()), profileDoc, new ReplaceOptions().upsert(true));
        }
    }

    /**
     * Create a document from a profile
     *
     * @param playerUUID the UUID of the player
     * @param profile the profile
     * @return the document
     */
    private Document createDocument(UUID playerUUID, Profile profile) {
        String username = Bukkit.getOfflinePlayer(playerUUID).getName();
        PlayerSettings playerSettings = profile.getPlayerSettings();
        long firstJoined = System.currentTimeMillis();

        Document getDoc = FlowerCore.getInstance().getMongoManager().getCollection().find(eq("UUID", playerUUID.toString())).first();
        firstJoined = (getDoc != null) ? getDoc.getLong("firstjoined") : firstJoined;

        long lastOnline = 0;
        Player player = Bukkit.getPlayer(playerUUID);

        if (player != null) {
            lastOnline = player.getLastPlayed();
        }

        String ip = (player != null) ? player.getAddress().getAddress().getHostAddress() : "null";
        Document doc = new Document("UUID", playerUUID.toString())
                .append("username", username)
                .append("currentIpAddress", ip)
                .append("firstjoined", firstJoined)
                .append("lastOnline", lastOnline)
                .append("rank", profile.getRank().getName())
                .append("tag", (profile.getTag() != null) ? profile.getTag().getName() : "none")
                .append("punishments", PunishmentSerializer.serialize(profile.getPunishments()));

        Document optionDocument = new Document();
        optionDocument.append("privateMessagesEnabled", playerSettings.isPrivateMessagesEnabled());
        optionDocument.append("messageSoundsEnabled", playerSettings.isMessageSoundsEnabled());
        optionDocument.append("globalChatEnabled", playerSettings.isGlobalChatEnabled());
        doc.append("option", optionDocument);

        return doc;
    }

    /**
     * Check if a collection exists
     *
     * @param database the database
     * @param collectionName the name of the collection
     * @return if the collection exists
     */
    private boolean collectionExists(MongoDatabase database, String collectionName) {
        return database.listCollectionNames().into(new ArrayList<>()).contains(collectionName);
    }

    public void close() {
        if (getMongoClient() != null) {
            getMongoClient().close();
        }
    }
}