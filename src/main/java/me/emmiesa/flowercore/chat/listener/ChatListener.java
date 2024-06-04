package me.emmiesa.flowercore.chat.listener;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.profile.Profile;
import me.emmiesa.flowercore.punishment.Punishment;
import me.emmiesa.flowercore.punishment.PunishmentType;
import me.emmiesa.flowercore.tag.Tag;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.lang.String;
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

        Profile profile = plugin.getProfileManager().getProfile(playerUUID);
        List<Punishment> punishments = profile.getPunishments();
        if (punishments == null) {
            return;
        }

        for (Punishment punishment : punishments) {
            if (punishment.isActive() && punishment.getType().equals(PunishmentType.MUTE)) {
                for (String message : plugin.getConfig("messages.yml").getStringList("punishments.mute")) {
                    player.sendMessage(CC.translate(message)
                            .replace("%duration%", punishment.getDuration())
                            .replace("%reason%", punishment.getReason())
                            .replace("%muted-by%", punishment.getByString())
                    );
                }
                event.setCancelled(true);
                return;
            }
        }

        if (!plugin.getProfileManager().getProfile(playerUUID).getPlayerSettings().isGlobalChatEnabled()) {
            player.sendMessage(CC.translate("&cYou've disabled global chat!"));
            event.getRecipients().remove(event.getPlayer());
            event.setCancelled(true);
            return;
        }

        boolean translate = player.hasPermission("flowercore.staff");
        String prefix = CC.translate(plugin.getProfileManager().getRank(playerUUID).getPrefix());
        String suffix = CC.translate(plugin.getProfileManager().getRank(playerUUID).getSuffix());

        Tag tag = plugin.getProfileManager().getTag(playerUUID);
        String tagPlaceholder = (tag != null) ? CC.translate(tag.getDisplayName()) : "";

        String message = translate ? CC.translate(event.getMessage()) : event.getMessage();
        String chatFormat = plugin.getConfig("settings.yml").getString("chat.format")
                .replace("{prefix}", prefix)
                .replace("{player}", player.getName())
                .replace("{message}", message)
                .replace("{suffix}", suffix)
                .replace("{tag}", tagPlaceholder);

        event.setFormat(chatFormat);

        if (FlowerCore.getInstance().getChatRepository().isChatMuted()) {
            if (player.hasPermission("flowercore.staff")) {
                return;
            }

            event.setCancelled(true);
            player.sendMessage(CC.translate("&cChat is currently muted!"));
        }
    }
}