package me.emmy.core.grant.command;

import me.emmy.core.grant.menu.grants.GrantsMenu;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 03/06/2024 - 20:38
 */
public class GrantsCommand extends BaseCommand {
    @Override
    @Command(name = "grants", permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        if (command.length() == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /grants (player)");
            return;
        }

        String playerName = command.getArgs(0);
        Player target = Bukkit.getPlayerExact(playerName);
        if (target == null) {
            player.sendMessage(CC.translate("&cNo player matching &4" + playerName + " &cis connected to this server."));
            return;
        }

        UUID targetUUID = target.getUniqueId();

        new GrantsMenu(playerName, targetUUID).openMenu(player);
    }
}
