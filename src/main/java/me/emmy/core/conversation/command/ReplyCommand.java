package me.emmy.core.conversation.command;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class ReplyCommand extends BaseCommand {
    @Override
    @Command(name = "reply", aliases = {"r"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        UUID lastConversantUUID = FlowerCore.getInstance().getConversationHandler().getLastConversant(player.getUniqueId());

        if (command.getArgs().length < 1) {
            if (lastConversantUUID == null) {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("private-messages.no-recent-conversation")));
            } else {
                Player lastConversant = FlowerCore.getInstance().getServer().getPlayer(lastConversantUUID);
                if (lastConversant != null) {
                    player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("private-messages.last-conversant").replace("%conversant%", lastConversant.getName())));
                } else {
                    player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("private-messages.recent-conversant-offline")));
                }
            }
            return;
        }

        if (lastConversantUUID == null) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("private-messages.nobody-to-reply")));
            return;
        }

        Player targetPlayer = FlowerCore.getInstance().getServer().getPlayer(lastConversantUUID);
        if (targetPlayer == null) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("private-messages.recent-conversant-offline")));
            return;
        }

        String message = String.join(" ", command.getArgs());

        FlowerCore.getInstance().getConversationHandler().sendMessage(player.getUniqueId(), targetPlayer.getUniqueId(), message);
    }
}
