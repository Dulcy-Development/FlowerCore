package me.emmiesa.flowercore.conversation.command;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class MessageCommand extends BaseCommand {
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

        Player targetPlayer = FlowerCore.getInstance().getServer().getPlayerExact(target);

        if (sender.equals(targetPlayer)){
            sender.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("private-messages.target-self")));
            return;
        }

        if (targetPlayer == null) {
            sender.sendMessage(CC.translate(Locale.PLAYER_NOT_ONLINE).replace("%player%", command.getArgs(0)));
            return;
        }

        FlowerCore.getInstance().getConversationHandler().sendMessage(sender.getUniqueId(), targetPlayer.getUniqueId(), message);
    }
}
