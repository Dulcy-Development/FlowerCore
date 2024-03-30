package me.emmiesa.flowercore.listeners;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.tags.Tag;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        boolean translate = player.hasPermission("flowercore.staff");

        String prefix = CC.translate(FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix());
        String suffix = CC.translate(FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getSuffix());
        Tag tag = FlowerCore.getInstance().getPlayerManager().getTag(playerUUID);
        String tagPlaceholder = (tag != null) ? CC.translate(tag.getDisplayName()) : "";

        String message = translate ? CC.translate(event.getMessage()) : event.getMessage();

        String chatFormat = FlowerCore.getInstance().getConfig("settings.yml").getString("chat.format")
                .replace("{prefix}", prefix)
                .replace("{player}", player.getName())
                .replace("{message}", message)
                .replace("{suffix}", suffix)
                .replace("{tag}", tagPlaceholder);

        event.setFormat(chatFormat);
    }
}

    /*@EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        event.setFormat(CC.translate(FlowerCore.getInstance().getConfig("settings.yml").getString("chat.format")
                .replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix())
                .replace("%player%", player.getName())
                .replace("%message%", event.getMessage())
                .replace("%suffix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getSuffix())));
    }
}*/