package me.emmiesa.flowercore.handler;

import lombok.Getter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

@Getter
public class ConversationHandler {

    private final HashMap<UUID, UUID> conversations;

    public ConversationHandler() {
        conversations = new HashMap<>();
    }

    public void sendMessage(UUID sender, UUID target, String message) {
        conversations.put(sender, target);
        conversations.put(target, sender);

        Player senderPlayer = Bukkit.getServer().getPlayer(sender);
        Player targetPlayer = Bukkit.getServer().getPlayer(target);

        if (targetPlayer == null && senderPlayer != null) {
            senderPlayer.sendMessage(CC.translate("&cThat player is currently offline."));
            return;
        }

        assert senderPlayer != null;
        if (!FlowerCore.getINSTANCE().getPlayerManager().getProfile(senderPlayer.getUniqueId()).getPlayerSettings().isPrivateMessagesEnabled()) {
            senderPlayer.sendMessage(CC.translate("&cYou don't have your private messages enabled."));
            return;
        }

        if (!FlowerCore.getINSTANCE().getPlayerManager().getProfile(targetPlayer.getUniqueId()).getPlayerSettings().isPrivateMessagesEnabled()) {
            senderPlayer.sendMessage(CC.translate("&cThat player doesn't have their private messages enabled."));
            return;
        }

        targetPlayer.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("private-messages.conversation.from")
                .replace("%sender%", FlowerCore.getINSTANCE().getPlayerManager().getPlayerRankColor(senderPlayer.getUniqueId()) + senderPlayer.getName())
                .replace("%message%", message)
        ));

        if (FlowerCore.getINSTANCE().getPlayerManager().getProfile(senderPlayer.getUniqueId()).getPlayerSettings().isMessageSoundsEnabled()) {
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
        }

        senderPlayer.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("private-messages.conversation.to")
                .replace("%target%", FlowerCore.getINSTANCE().getPlayerManager().getPlayerRankColor(targetPlayer.getUniqueId()) + targetPlayer.getName())
                .replace("%message%", message)
        ));

        if (FlowerCore.getINSTANCE().getPlayerManager().getProfile(senderPlayer.getUniqueId()).getPlayerSettings().isMessageSoundsEnabled()) {
            senderPlayer.playSound(senderPlayer.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
        }
    }

    public UUID getLastConversant(UUID player) {
        return conversations.get(player);
    }
}