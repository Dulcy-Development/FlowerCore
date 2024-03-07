package me.emmiesa.flowercore.commands.global.conversation;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class MessageCommand extends BaseCommand {

    /*
     *
     * This needs to be re-done. It is basic as +as#dv+!
     *
     */

    @Override
    @Command(name = "message", aliases = {"msg", "tell", "whisper"})
    public void onCommand(CommandArgs command) {
        Player sender = command.getPlayer();

        if (command.length() < 2) {
            sender.sendMessage(CC.translate("&cUsage: /message (player) (message)"));
            return;
        }

        String target = command.getArgs(0);
        String message = String.join(" ", java.util.Arrays.copyOfRange(command.getArgs(), 1, command.getArgs().length));

        Player targetPlayer = FlowerCore.getInstance().getServer().getPlayer(target);

        if (sender.equals(targetPlayer)){
            sender.sendMessage(CC.translate("&cYou can't message yourself."));
            return;
        }

        if (targetPlayer == null) {
            sender.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        FlowerCore.getInstance().getConversationHandler().sendMessage(sender.getUniqueId(), targetPlayer.getUniqueId(), message);
    }
}
