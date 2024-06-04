package me.emmiesa.flowercore.grant.command;

import me.emmiesa.flowercore.grant.menu.grants.GrantsMenu;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 03/06/2024 - 20:38
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
