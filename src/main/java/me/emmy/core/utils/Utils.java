package me.emmy.core.utils;

import lombok.experimental.UtilityClass;
import me.emmy.core.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 14/07/2024 - 14:52
 */
@UtilityClass
public class Utils {

    /**
     * Broadcast a message to all players
     *
     * @param message The message to broadcast
     */
    public void broadcastMessage(String message) {
        List<Player> getOnlinePlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        getOnlinePlayers.forEach(player -> player.sendMessage(CC.translate(message)));
    }

    /**
     * Broadcast a message to all players
     *
     * @param message The message to broadcast
     */
    public void broadcastBungeeMessage(String message) {
        Bukkit.getServer().sendPluginMessage(Bukkit.getPluginManager().getPlugin("FlowerCore"), "BungeeCord", CC.translate(message).getBytes());
    }
}