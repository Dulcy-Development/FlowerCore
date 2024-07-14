package me.emmy.core.command.admin.troll;

import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TrapCommand extends BaseCommand {
    @Override
    @Command(name = "trap", inGameOnly = false, permission = "flowercore.command.trap")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        Player target;

        if (args.length() > 0) {
            target = Bukkit.getPlayerExact(args.getArgs(0));
            if (target == null) {
                sender.sendMessage(CC.translate("&cPlayer not found."));
                return;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(CC.translate("&cUsage: /trap (player)"));
                return;
            }
            target = (Player) sender;
        }

        Location loc = target.getLocation();
        World world = target.getWorld();

        int px = loc.getBlockX();
        int py = loc.getBlockY();
        int pz = loc.getBlockZ();

        int[][] positions = {
                {1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1},
                {1, 1, 0}, {-1, 1, 0}, {0, 1, 1}, {0, 1, -1},
                {1, 2, 0}, {-1, 2, 0}, {0, 2, 1}, {0, 2, -1},
                {0, -1, 0}, {0, 3, 0}
        };

        for (int[] pos : positions) {
            world.getBlockAt(px + pos[0], py + pos[1], pz + pos[2]).setType(Material.GLASS);
        }

        sender.sendMessage(CC.translate("&bYou've trapped &3" + target.getDisplayName() + " &bin glass. They can probably no longer move! \n&cIf they escaped, try doing '/capture " + target.getDisplayName() + "&c'"));
        target.sendMessage(CC.translate("&c(!) You've been trapped by &4" + args.getPlayer().getDisplayName() + "&c. You're in trouble (!)"));
    }
}

