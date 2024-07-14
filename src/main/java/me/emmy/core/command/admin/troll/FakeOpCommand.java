package me.emmy.core.command.admin.troll;

import me.emmy.core.locale.Locale;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 12/05/2024 - 09:28
 */
public class FakeOpCommand extends BaseCommand {
    @Override
    @Command(name = "fakeop", permission = "flower.command.fakeop", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        String targetName = args[0];
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        String senderName;

        if (targetPlayer == null) {
            sender.sendMessage(CC.translate(Locale.PLAYER_NOT_ONLINE).replace("%player%", targetName));
            return;
        }

        if (sender instanceof Player) {
            senderName = sender.getName();
        } else {
            senderName = "Server";
        }

        targetPlayer.sendMessage(CC.translate("&7&o[" + senderName + "&7&o: Opped " + targetPlayer.getName() + "&7&o]"));

        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            onlinePlayers.sendMessage(CC.translate("&7&o[" + senderName + "&7&o: Opped " + targetPlayer.getName() + "&7&o]"));
        }
    }
}