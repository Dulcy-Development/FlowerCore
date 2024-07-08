package me.emmiesa.flowercore.conversation;

import lombok.Getter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 08/03/2024 - 11:23
 */
@Getter
public class ConversationHandler {

    private final HashMap<UUID, UUID> conversations;

    /**
     * Constructor for the ConversationHandler.
     */
    public ConversationHandler() {
        conversations = new HashMap<>();
    }

    /**
     * Sends a message to a player.
     *
     * @param sender  The sender of the message.
     * @param target  The target of the message.
     * @param message The message to send.
     */
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
        if (!FlowerCore.getInstance().getProfileRepository().getProfile(senderPlayer.getUniqueId()).getPlayerSettings().isPrivateMessagesEnabled()) {
            senderPlayer.sendMessage(CC.translate("&cYou don't have your private messages enabled."));
            return;
        }

        if (!FlowerCore.getInstance().getProfileRepository().getProfile(targetPlayer.getUniqueId()).getPlayerSettings().isPrivateMessagesEnabled()) {
            senderPlayer.sendMessage(CC.translate("&cThat player doesn't have their private messages enabled."));
            return;
        }

        targetPlayer.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("private-messages.conversation.from")
                .replace("%sender%", FlowerCore.getInstance().getProfileRepository().getPlayerRankColor(senderPlayer.getUniqueId()) + senderPlayer.getName())
                .replace("%message%", message)
        ));

        if (FlowerCore.getInstance().getProfileRepository().getProfile(senderPlayer.getUniqueId()).getPlayerSettings().isMessageSoundsEnabled()) {
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
        }

        senderPlayer.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("private-messages.conversation.to")
                .replace("%target%", FlowerCore.getInstance().getProfileRepository().getPlayerRankColor(targetPlayer.getUniqueId()) + targetPlayer.getName())
                .replace("%message%", message)
        ));

        if (FlowerCore.getInstance().getProfileRepository().getProfile(senderPlayer.getUniqueId()).getPlayerSettings().isMessageSoundsEnabled()) {
            senderPlayer.playSound(senderPlayer.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
        }
    }

    /**
     * Gets the last conversant of a player.
     *
     * @param player The player to get the last conversant of.
     * @return The last conversant of the player.
     */
    public UUID getLastConversant(UUID player) {
        return conversations.get(player);
    }
}