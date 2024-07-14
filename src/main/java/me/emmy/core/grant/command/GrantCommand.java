package me.emmy.core.grant.command;

import me.emmy.core.grant.menu.grant.GrantMenu;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class GrantCommand extends BaseCommand {
    @Override
    @Command(name = "grant", permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        if (command.getArgs().length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /grant (player)");
            return;
        }

        String playerName = command.getArgs(0);
        Player target = Bukkit.getPlayerExact(playerName);
        if (target == null) {
            player.sendMessage(CC.translate("&cNo player matching &4" + playerName + " &cis connected to this server."));
            return;
        }

        new GrantMenu(playerName).openMenu(player);
    }
}
