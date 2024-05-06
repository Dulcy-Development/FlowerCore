package me.emmiesa.flowercore.commands.global.conversation;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class ReplyCommand extends BaseCommand {

    /*
     *
     * This needs to be re-done. It is basic as +as#dv+!
     *
     */

    @Override
    @Command(name = "reply", aliases = {"r"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        UUID lastConversantUUID = FlowerCore.getINSTANCE().getConversationHandler().getLastConversant(player.getUniqueId());

        if (command.getArgs().length < 1) {
            if (lastConversantUUID == null) {
                player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("private-messages.no-recent-conversation")));
            } else {
                Player lastConversant = FlowerCore.getINSTANCE().getServer().getPlayer(lastConversantUUID);
                if (lastConversant != null) {
                    player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("private-messages.last-conversant").replace("%conversant%", lastConversant.getName())));
                } else {
                    player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("private-messages.recent-conversant-offline")));
                }
            }
            return;
        }

        if (lastConversantUUID == null) {
            player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("private-messages.nobody-to-reply")));
            return;
        }

        Player targetPlayer = FlowerCore.getINSTANCE().getServer().getPlayer(lastConversantUUID);
        if (targetPlayer == null) {
            player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("private-messages.recent-conversant-offline")));
            return;
        }

        String message = String.join(" ", command.getArgs());

        FlowerCore.getINSTANCE().getConversationHandler().sendMessage(player.getUniqueId(), targetPlayer.getUniqueId(), message);
    }
}
