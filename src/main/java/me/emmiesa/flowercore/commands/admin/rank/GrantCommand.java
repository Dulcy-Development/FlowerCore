package me.emmiesa.flowercore.commands.admin.rank;

import me.emmiesa.flowercore.menus.grant.GrantMenu;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GrantCommand extends BaseCommand {

    @Command(name = "grant", permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        if (args.getArgs().length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /grant (player)");
            return;
        }

        String playerName = args.getArgs(0);
        Player target = Bukkit.getPlayer(playerName);
        if (target == null) {
            player.sendMessage(CC.translate("&cNo player matching &4" + playerName + " &cis connected to this server."));
            return;
        }

        new GrantMenu(playerName).openMenu(player);
    }
}
