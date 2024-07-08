package me.emmiesa.flowercore.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 20/03/2024 - 23:13
 */

public class AntiTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("flowercore.staff")) {
                return new ArrayList<>();
            }
        }

        return null;
    }
}