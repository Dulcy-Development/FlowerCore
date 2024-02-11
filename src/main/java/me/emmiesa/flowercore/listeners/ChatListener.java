package me.emmiesa.flowercore.listeners;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        boolean translate = player.hasPermission("flowercore.staff");

        String prefix = CC.translate(FlowerCore.instance.getPlayerManager().getRank(playerUUID).getPrefix());
        String suffix = CC.translate(FlowerCore.instance.getPlayerManager().getRank(playerUUID).getSuffix());

        String message = translate ? CC.translate(event.getMessage()) : event.getMessage();

        String chatFormat = FlowerCore.instance.getConfig("settings.yml").getString("chat.format")
                .replace("%prefix%", prefix)
                .replace("%player%", player.getName())
                .replace("%message%", message)
                .replace("%suffix%", suffix);

        event.setFormat(chatFormat);
    }
}

    /*@EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        event.setFormat(CC.translate(FlowerCore.instance.getConfig("settings.yml").getString("chat.format")
                .replace("%prefix%", FlowerCore.instance.getPlayerManager().getRank(playerUUID).getPrefix())
                .replace("%player%", player.getName())
                .replace("%message%", event.getMessage())
                .replace("%suffix%", FlowerCore.instance.getPlayerManager().getRank(playerUUID).getSuffix())));
    }
}*/