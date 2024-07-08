package me.emmiesa.flowercore.punishment.command;

import me.emmiesa.flowercore.punishment.menu.PunishHistoryMenu;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        if (command.getArgs().length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /punishhistory (player)");
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
