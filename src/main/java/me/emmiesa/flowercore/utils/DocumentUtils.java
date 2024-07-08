package me.emmiesa.flowercore.utils;

import com.mongodb.client.MongoCollection;
import lombok.experimental.UtilityClass;
import me.emmiesa.flowercore.FlowerCore;
import org.bson.Document;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 02/04/2024 - 14:48
 */
@UtilityClass
public class DocumentUtils {

    public String getPlayerIpAddressFromDocument(Document playerDocument) {
        return playerDocument.getString("currentIpAddress");
    }

    public Document getPlayerDocument(String playerName) {
        MongoCollection<Document> collection = FlowerCore.getInstance().getMongoService().getCollection();
        return collection.find(eq("username", playerName)).first();
    }

    public UUID getPlayerUUIDFromDocument(String playerName) {
        MongoCollection<Document> collection = FlowerCore.getInstance().getMongoService().getCollection();
        Document playerDoc = collection.find(eq("username", playerName)).first();
        return playerDoc != null ? UUID.fromString(playerDoc.getString("UUID")) : null;
    }
}