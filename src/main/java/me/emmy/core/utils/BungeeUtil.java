package me.emmy.core.utils;

import lombok.experimental.UtilityClass;
import me.emmy.core.FlowerCore;
import me.emmy.core.utils.chat.CC;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 24/03/2024 - 12:17
 */
@UtilityClass
public class BungeeUtil {

    /**
     * Sends a player to a server
     *
     * @param player The player to send
     * @param server The server to send the player to
     */
    public void sendPlayer(Player player, String server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("join.joining").replace("%server%", server)));
        player.sendPluginMessage(FlowerCore.getInstance(), "BungeeCord", b.toByteArray());
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("join.failed").replace("%server%", server)));
            }
        };
        task.runTaskLater(FlowerCore.getInstance(), 20);
    }
}