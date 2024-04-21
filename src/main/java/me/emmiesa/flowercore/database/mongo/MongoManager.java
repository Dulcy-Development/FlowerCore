package me.emmiesa.flowercore.database.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.playersettings.PlayerSettingsManager;
import me.emmiesa.flowercore.profile.HostAddressSerializer;
import me.emmiesa.flowercore.profile.Profile;
import me.emmiesa.flowercore.punishments.PunishmentSerializer;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
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

    public void startMongo() {
        String uri = FlowerCore.getInstance().getConfig("database.yml").getString("database.uri");
        mongoClient = MongoClients.create(uri);

        MongoDatabase database = getMongoClient().getDatabase(FlowerCore.getInstance().getConfig("database.yml").getString("database.database-name"));
        String collectionName = "profile";

        if (!collectionExists(database, collectionName)) {
            database.createCollection(collectionName);
        }

        collection = database.getCollection(collectionName);
    }

    public void initializeProfile(UUID playerUUID) {
        Document doc = getCollection().find(eq("UUID", playerUUID.toString())).first();
        Profile profile;

        if (doc == null) {
            PlayerSettingsManager defaultSettings = new PlayerSettingsManager(true, true, true);
            profile = createDefaultProfile(playerUUID, defaultSettings);
            saveProfile(profile);
        } else {
            Document optionDoc = doc.get("option", Document.class);
            boolean privateMessagesEnabled = optionDoc.getBoolean("privateMessagesEnabled", true);
            boolean soundsEnabled = optionDoc.getBoolean("messageSoundsEnabled", true);
            boolean globalChatEnabled = optionDoc.getBoolean("globalChatEnabled", true);

            // (global, private, sounds) needs to be in the correct order as above because otherwise it will set the wrong settings to false or true
            PlayerSettingsManager playerSettingsManager = new PlayerSettingsManager(globalChatEnabled, privateMessagesEnabled, soundsEnabled);
            profile = createProfile(playerUUID, doc, playerSettingsManager);
        }
        FlowerCore.getInstance().getPlayerManager().addRank(profile);
    }

    private Profile createDefaultProfile(UUID playerUUID, PlayerSettingsManager playerSettingsManager) {
        return Profile.builder()
                .uuid(playerUUID)
                .rank(FlowerCore.getInstance().getRanksManager().getDefaultRank())
                .tag(null)
                .playerSettingsManager(playerSettingsManager)
                .build();
    }

    private Profile createProfile(UUID playerUUID, Document doc, PlayerSettingsManager playerSettingsManager) {
        return Profile.builder()
                .uuid(playerUUID)
                .rank(FlowerCore.getInstance().getRanksManager().getRank(doc.getString("rank")))
                .punishments(PunishmentSerializer.deserialize(doc.getList("punishments", String.class)))
                .playerSettingsManager(playerSettingsManager)
                .tag(FlowerCore.getInstance().getTagsManager().getTag(doc.getString("tag")))
                .build();
    }

    public void saveAllPlayerData() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            saveProfile(player.getUniqueId());
        }
    }

    public void saveProfile(Profile profile) {
        UUID playerUUID = profile.getUuid();

        if (playerUUID != null) {
            Document profileDoc = createDocument(playerUUID, profile);
            getCollection().replaceOne(eq("UUID", playerUUID.toString()), profileDoc, new ReplaceOptions().upsert(true));
        }
    }

    public void saveProfile(UUID playerUUID) {
        Profile profile = FlowerCore.getInstance().getPlayerManager().getProfiles().get(playerUUID);

        if (profile != null) {
            Document profileDoc = createDocument(playerUUID, profile);
            getCollection().replaceOne(eq("UUID", playerUUID.toString()), profileDoc, new ReplaceOptions().upsert(true));
        }
    }

    private Document createDocument(UUID playerUUID, Profile profile) {
        String username = Bukkit.getOfflinePlayer(playerUUID).getName();
        PlayerSettingsManager playerSettingsManager = profile.getPlayerSettingsManager();
        long firstJoined = System.currentTimeMillis();

        Document getDoc = FlowerCore.getInstance().getMongoManager().getCollection().find(eq("UUID", playerUUID.toString())).first();
        firstJoined = (getDoc != null) ? getDoc.getLong("firstjoined") : firstJoined;

        long lastOnline = 0;
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            lastOnline = player.getLastPlayed();
        }

        String ip = (player != null) ? player.getAddress().getAddress().getHostAddress() : "null";

        List<String> ipList = getPlayerIpList(player);
        String ipListJson = HostAddressSerializer.serialize(ipList);

        Document doc = new Document("UUID", playerUUID.toString())
                .append("username", username)
                .append("currentIpAddress", ip)
                .append("ipAddresses", ipListJson)
                .append("firstjoined", firstJoined)
                .append("lastOnline", lastOnline)
                .append("rank", profile.getRank().getName())
                .append("tag", (profile.getTag() != null) ? profile.getTag().getName() : "none")
                .append("punishments", PunishmentSerializer.serialize(profile.getPunishments()));

        Document optionDocument = new Document();
        optionDocument.append("privateMessagesEnabled", playerSettingsManager.isPrivateMessagesEnabled());
        optionDocument.append("messageSoundsEnabled", playerSettingsManager.isMessageSoundsEnabled());
        optionDocument.append("globalChatEnabled", playerSettingsManager.isGlobalChatEnabled());
        doc.append("option", optionDocument);

        return doc;
    }


    private boolean collectionExists(MongoDatabase database, String collectionName) {
        return database.listCollectionNames().into(new ArrayList<>()).contains(collectionName);
    }

    public void close() {
        if (getMongoClient() != null) {
            getMongoClient().close();
        }
    }

    private List<String> getPlayerIpList(Player player) {
        List<String> ipList = new ArrayList<>();
        if (player != null) {
            ipList.add(player.getAddress().getAddress().getHostAddress());
        }
        return ipList;
    }
}