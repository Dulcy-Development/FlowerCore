package me.emmiesa.flowercore.listeners;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.profile.Profile;
import me.emmiesa.flowercore.punishments.Punishment;
import me.emmiesa.flowercore.punishments.PunishmentType;
import me.emmiesa.flowercore.tags.Tag;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class ChatListener implements Listener {

    private final FlowerCore plugin = FlowerCore.getInstance();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        Profile profile = plugin.getPlayerManager().getProfile(playerUUID);
        List<Punishment> punishments = profile.getPunishments();
        if (punishments == null) {
            return;
        }

        for (Punishment punishment : punishments) {
            if (punishment.isActive() && punishment.getType().equals(PunishmentType.MUTE)) {
                player.sendMessage("");
                player.sendMessage(CC.translate("&cYou've been muted by &4" + punishment.getBy()));
                player.sendMessage(CC.translate(" &7Duration: &c" + punishment.getDuration()));
                player.sendMessage(CC.translate(" &7Reason: &c" + punishment.getReason()));
                player.sendMessage("");
                event.setCancelled(true);
                return;
            }
        }

        if (!plugin.getPlayerManager().getProfile(playerUUID).getPlayerSettings().isGlobalChatEnabled()) {
            player.sendMessage(CC.translate("&cYou've disabled global chat!"));
            event.getRecipients().remove(event.getPlayer());
            event.setCancelled(true);
            return;
        }

        boolean translate = player.hasPermission("flowercore.staff");
        String prefix = CC.translate(plugin.getPlayerManager().getRank(playerUUID).getPrefix());
        String suffix = CC.translate(plugin.getPlayerManager().getRank(playerUUID).getSuffix());

        Tag tag = plugin.getPlayerManager().getTag(playerUUID);
        String tagPlaceholder = (tag != null) ? CC.translate(tag.getDisplayName()) : "";

        String message = translate ? CC.translate(event.getMessage()) : event.getMessage();
        String chatFormat = plugin.getConfig("settings.yml").getString("chat.format")
                .replace("{prefix}", prefix)
                .replace("{player}", player.getName())
                .replace("{message}", message)
                .replace("{suffix}", suffix)
                .replace("{tag}", tagPlaceholder);

        event.setFormat(chatFormat);
    }
}