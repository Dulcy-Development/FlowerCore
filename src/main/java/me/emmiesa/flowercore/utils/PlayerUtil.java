package me.emmiesa.flowercore.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

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

}