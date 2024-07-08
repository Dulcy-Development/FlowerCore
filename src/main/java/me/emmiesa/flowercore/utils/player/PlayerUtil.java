package me.emmiesa.flowercore.utils.player;

import com.mongodb.client.MongoCollection;
import me.emmiesa.flowercore.FlowerCore;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 02/04/2024 - 14:48
 */

public class PlayerUtil {
    public static UUID getUUIDByName(String playerName) {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName() != null && player.getName().equalsIgnoreCase(playerName)) {
                return player.getUniqueId();
            }
        }
        return null;
    }

    public static String getPlayerIpAddressFromDocument(Document playerDocument) {
        return playerDocument.getString("currentIpAddress");
    }

    public static Document getPlayerDocument(String playerName) {
        MongoCollection<Document> collection = FlowerCore.getInstance().getMongoService().getCollection();
        return collection.find(eq("username", playerName)).first();
    }

    public static UUID getPlayerUUIDFromDocument(String playerName) {
        MongoCollection<Document> collection = FlowerCore.getInstance().getMongoService().getCollection();
        Document playerDoc = collection.find(eq("username", playerName)).first();
        return playerDoc != null ? UUID.fromString(playerDoc.getString("UUID")) : null;
    }

    public static List<String> getPlayerIpList(Player player) {
        List<String> ipList = new ArrayList<>();
        if (player != null) ipList.add(player.getAddress().getAddress().getHostAddress());
        return ipList;
    }
}