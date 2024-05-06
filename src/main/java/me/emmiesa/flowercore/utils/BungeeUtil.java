package me.emmiesa.flowercore.utils;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 24/03/2024 - 12:17
 */

public class BungeeUtil {

    public static void sendPlayer(Player player, String server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("join.joining").replace("%server%", server)));
        player.sendPluginMessage(FlowerCore.getINSTANCE(), "BungeeCord", b.toByteArray());
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("join.failed").replace("%server%", server)));
            }
        };
        task.runTaskLater(FlowerCore.getINSTANCE(), 20);
    }
}