package me.emmy.core.punishment.command;

import me.emmy.core.punishment.menu.PunishHistoryMenu;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 09/05/2024 - 19:14
 */
public class PunishHistoryCommand extends BaseCommand {
    @Override
    @Command(name = "punishhistory", aliases = "punishi", permission = "flower")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /punishhistory (player)"));
            return;
        }

        String playerName = command.getArgs(0);
        Player target = Bukkit.getPlayerExact(playerName);

        if (target == null) {
            player.sendMessage(CC.translate("&cNo player matching &4" + playerName + " &cis connected to this server."));
            return;
        }

        new PunishHistoryMenu(playerName).openMenu(player);
    }
}
