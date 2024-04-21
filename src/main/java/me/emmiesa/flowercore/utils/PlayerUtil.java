package me.emmiesa.flowercore.utils;

import com.mongodb.client.MongoCollection;
import me.emmiesa.flowercore.FlowerCore;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 02/04/2024 - 14:48
 */

public class PlayerUtil {
    public static UUID findUUIDByName(String playerName) {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName() != null && player.getName().equalsIgnoreCase(playerName)) {
                return player.getUniqueId();
            }
        }
        return null;
    }

    public static String getPlayerIpAddressFromDoc(Document playerDoc) {
        return playerDoc.getString("currentIpAddress");
    }

    public static Document getPlayerDocument(CommandSender sender, String playerName) {
        MongoCollection<Document> collection = FlowerCore.getInstance().getMongoManager().getCollection();
        return collection.find(eq("username", playerName)).first();
    }

    public static UUID getPlayerUUIDfromDocument(String playerName) {
        MongoCollection<Document> collection = FlowerCore.getInstance().getMongoManager().getCollection();
        Document playerDoc = collection.find(eq("username", playerName)).first();
        if (playerDoc != null) {
            return UUID.fromString(playerDoc.getString("UUID"));
        } else {
            return null;
        }
    }

}