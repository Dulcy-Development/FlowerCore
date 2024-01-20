package me.emmiesa.flowercore.listeners;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        event.setFormat(CC.translate(FlowerCore.instance.getConfig("settings.yml").getString("chat.format")
                .replace("%prefix%", FlowerCore.instance.getPlayerManager().getRank(playerUUID).getPrefix())
                .replace("%player%", player.getName())
                .replace("%message%", event.getMessage())
                .replace("%suffix%", FlowerCore.instance.getPlayerManager().getRank(playerUUID).getSuffix())));
    }
}

    /*@EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        //Global players
        if (!playerUUID.equals(UUID.fromString("ce4edfc7-7058-4266-9dd6-17c8b9f43dbe"))) {
            if (FlowerCore.instance.getConfig("settings.yml").getBoolean("chat-format.enabled")) {
                String formatPath = "chat-format.format";
                String format = FlowerCore.instance.getConfig("settings.yml").getString(formatPath);

                if (format != null && !format.isEmpty()) {
                    String formattedMessage = CC.translate(format)
                            .replace("%player%", player.getName())
                            //.replace("%server%", Lang.SERVER_NAME)
                            .replace("%message%", event.getMessage());
                    event.setFormat(formattedMessage);
                }
            }

            if (FlowerCore.instance.getConfig("settings.yml").getBoolean("op-format.enabled")) {
                String opformatPath = "op-format.format";
                String opformat = FlowerCore.instance.getConfig("settings.yml").getString(opformatPath);

                if (player.hasPermission("*")) {
                    String OPformattedMessage = CC.translate(opformat)
                            .replace("%player%", player.getName())
                            //.replace("%server%", Lang.SERVER_NAME)
                            .replace("%message%", event.getMessage());
                    event.setFormat(OPformattedMessage);
                }
            }
        }

        //Emmiesa
        if (playerUUID.equals(UUID.fromString("ce4edfc7-7058-4266-9dd6-17c8b9f43dbe")) && !player.isOp()) {
            // Special format for player with specific UUID and not OP
            String specialFormat = "&7[&4Owner&7] &r&4&lೋ&2&lღ&c%player%&2&lღ&4&lೋ&7: &r&f%message%";
            String formattedMessage = CC.translate(specialFormat)
                    .replace("%player%", player.getName())
                    .replace("%message%", event.getMessage());
            event.setFormat(formattedMessage);
        } else {
            if (FlowerCore.instance.getConfig("settings.yml").getBoolean("op-format.enabled")) {
                if (playerUUID.equals(UUID.fromString("ce4edfc7-7058-4266-9dd6-17c8b9f43dbe")) && player.isOp()) {
                    String opformatPath = "op-format.format";
                    String opformat = FlowerCore.instance.getConfig("settings.yml").getString(opformatPath);
                    String OPformattedMessage = CC.translate(opformat)
                            .replace("%player%", player.getName())
                            //.replace("%server%", Lang.SERVER_NAME)
                            .replace("%message%", event.getMessage());
                    event.setFormat(OPformattedMessage);
                }
            }
        }
    }
}*/