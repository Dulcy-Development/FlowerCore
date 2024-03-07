package me.emmiesa.flowercore.commands.global.conversation;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.UUID;

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

        UUID lastConversantUUID = FlowerCore.getInstance().getConversationHandler().getLastConversant(player.getUniqueId());

        if (command.getArgs().length < 1) {
            if (lastConversantUUID == null) {
                player.sendMessage(CC.translate("&cYou haven't messaged anyone recently."));
            } else {
                Player lastConversant = FlowerCore.getInstance().getServer().getPlayer(lastConversantUUID);
                if (lastConversant != null) {
                    player.sendMessage(CC.translate("&bYou're communicating with: &3" + lastConversant.getName()));
                } else {
                    player.sendMessage(CC.translate("&cThe player you last conversed with is currently offline."));
                }
            }
            return;
        }

        if (lastConversantUUID == null) {
            player.sendMessage(CC.translate("&cYou have no one to reply to."));
            return;
        }

        Player targetPlayer = FlowerCore.getInstance().getServer().getPlayer(lastConversantUUID);
        if (targetPlayer == null) {
            player.sendMessage(CC.translate("&cThe player you're trying to reply to is currently offline."));
            return;
        }

        String message = String.join(" ", command.getArgs());

        FlowerCore.getInstance().getConversationHandler().sendMessage(player.getUniqueId(), targetPlayer.getUniqueId(), message);
    }
}
