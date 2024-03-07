package me.emmiesa.flowercore.handler;

import lombok.Getter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;

@Getter
public class ConversationHandler {

    /*
     *
     * This needs to be re-done. It is basic as +as#dv+!
     *
     */

    private final HashMap<UUID, UUID> conversations;

    public ConversationHandler() {
        conversations = new HashMap<>();
    }

    public void sendMessage(UUID sender, UUID target, String message) {
        conversations.put(sender, target);
        conversations.put(target, sender);

        Player senderPlayer = Bukkit.getServer().getPlayer(sender);
        Player targetPlayer = Bukkit.getServer().getPlayer(target);

        if (targetPlayer == null) {
            if (senderPlayer != null) {
                senderPlayer.sendMessage(CC.translate("&cThat player is currently offline."));
            }
            return;
        }

        targetPlayer.sendMessage(CC.translate("&f(&bFrom " + FlowerCore.getInstance().getPlayerManager().getRank(senderPlayer.getUniqueId()).getPrefix() + senderPlayer.getName() + "&f) " + message));

        senderPlayer.sendMessage(CC.translate("&f(&bTo " + FlowerCore.getInstance().getPlayerManager().getRank(targetPlayer.getUniqueId()).getPrefix() + targetPlayer.getName() + "&f) " + message));
    }

    public UUID getLastConversant(UUID player) {
        return conversations.get(player);
    }
}