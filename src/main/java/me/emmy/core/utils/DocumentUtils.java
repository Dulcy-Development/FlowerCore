package me.emmy.core.utils;

import com.mongodb.client.MongoCollection;
import lombok.experimental.UtilityClass;
import me.emmy.core.FlowerCore;
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

    /**
     * Get the player's IP address from a document
     *
     * @param playerDocument The document to get the IP address from
     * @return The IP address of the player
     */
    public String getPlayerIpAddressFromDocument(Document playerDocument) {
        return playerDocument.getString("currentIpAddress");
    }

    /**
     * Get the player's document
     *
     * @param playerName The name of the player to get the document from
     * @return The document of the player
     */
    public Document getPlayerDocument(String playerName) {
        MongoCollection<Document> collection = FlowerCore.getInstance().getMongoService().getCollection();
        return collection.find(eq("username", playerName)).first();
    }

    /**
     * Get the player's UUID from a document
     *
     * @param playerName The name of the player to get the UUID from
     * @return The UUID of the player
     */
    public UUID getPlayerUUIDFromDocument(String playerName) {
        MongoCollection<Document> collection = FlowerCore.getInstance().getMongoService().getCollection();
        Document playerDoc = collection.find(eq("username", playerName)).first();
        return playerDoc != null ? UUID.fromString(playerDoc.getString("UUID")) : null;
    }
}