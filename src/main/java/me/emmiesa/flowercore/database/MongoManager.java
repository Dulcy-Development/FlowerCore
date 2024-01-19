package me.emmiesa.flowercore.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.profile.Profile;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author lrxh
 * @project WavePractice
 * @since 12/4/2023
 */

@Getter
public class MongoManager {

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    public void startMongo() {
        String uri = FlowerCore.instance.getConfig("database.yml").getString("database.uri");
        mongoClient = MongoClients.create(uri);

        MongoDatabase database = getMongoClient().getDatabase("flower");
        String collectionName = FlowerCore.instance.getConfig("database.yml").getString("database.collection");

        if (!collectionExists(database, collectionName)) {
            database.createCollection(collectionName);
        }

        collection = database.getCollection(collectionName);
    }

    public void initializeProfile(UUID playerUUID) {
        Document doc = getCollection().find(eq("UUID", playerUUID.toString())).first();
        Profile profile;

        if (doc == null) {
            profile = createDefaultProfile(playerUUID);
            saveProfile(profile);
        } else {
            profile = createProfile(playerUUID, doc);
        }
        FlowerCore.instance.getPlayerManager().addRank(profile);
    }

    private Profile createDefaultProfile(UUID playerUUID) {
        return Profile.builder()
                .uuid(playerUUID)
                .rank(FlowerCore.instance.getRanksManager().getDefaultRank())
                .build();
    }

    private Profile createProfile(UUID playerUUID, Document doc) {
        return Profile.builder()
                .uuid(playerUUID)
                .rank(FlowerCore.instance.getRanksManager().getRank(doc.getString("rank")))
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
        Profile profile = FlowerCore.instance.getPlayerManager().getProfiles().get(playerUUID);

        if (profile != null) {
            Document profileDoc = createDocument(playerUUID, profile);
            getCollection().replaceOne(eq("UUID", playerUUID.toString()), profileDoc, new ReplaceOptions().upsert(true));
        }
    }

    private Document createDocument(UUID playerUUID, Profile profile) {
        return new Document("UUID", playerUUID.toString())
                .append("rank", profile.getRank().getName());
    }

    private boolean collectionExists(MongoDatabase database, String collectionName) {
        return database.listCollectionNames().into(new ArrayList<>()).contains(collectionName);
    }

    public void close() {
        if (getMongoClient() != null) {
            getMongoClient().close();
        }
    }
}