package me.emmiesa.flowercore.utils.player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 28/04/2024 - 20:25
 */

public class OfflinePlayerUtil {
    public static OfflinePlayer getOfflinePlayer(String playerName) {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName() != null && player.getName().equalsIgnoreCase(playerName)) {
                return player;
            }
        }
        return null;
    }
}
