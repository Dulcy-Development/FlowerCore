package me.emmiesa.flowercore.commands.admin.management;

import me.emmiesa.flowercore.menus.punishmenthistory.PunishHistoryMenu;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 09/05/2024 - 19:14
 */

public class PunishHistoryCommand extends BaseCommand {
    @Override
    @Command(name = "punishhistory", aliases = "punishi", permission = "flower")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        if (args.getArgs().length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /punishhistory (player)");
            return;
        }

        String playerName = args.getArgs(0);
        Player target = Bukkit.getPlayerExact(playerName);
        if (target == null) {
            player.sendMessage(CC.translate("&cNo player matching &4" + playerName + " &cis connected to this server."));
            return;
        }

        new PunishHistoryMenu(playerName).openMenu(player);
    }
}
