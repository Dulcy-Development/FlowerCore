package me.emmiesa.flowercore.command.admin.troll;

import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
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
public class CaptureCommand extends BaseCommand {
    @Override
    @Command(name = "capture", aliases = "jail", permission = "flowercore.command.capture")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() == 0) {
            sender.sendMessage(CC.translate("&cUsage: /capture (player)"));
            return;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayerExact(args.getArgs(0));
        if (target == null) {
            sender.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        target.teleport(player.getLocation());

        Location loc = player.getLocation();
        World world = loc.getWorld();

        int px = loc.getBlockX();
        int py = loc.getBlockY();
        int pz = loc.getBlockZ();

        for (int x = px - 2; x <= px + 2; x++) {
            for (int y = py - 2; y <= py + 3; y++) {
                for (int z = pz - 2; z <= pz + 2; z++) {
                    if (x == px - 2 || x == px + 2 || y == py - 2 || y == py + 3 || z == pz - 2 || z == pz + 2) {
                        world.getBlockAt(x, y, z).setType(Material.BARRIER);
                    }
                }
            }
        }

        sender.sendMessage(CC.translate("&aYou've captured &2" + target.getDisplayName() + " &aand put them in jail."));
        target.sendMessage(CC.translate("&cYou've been captured and put in jail by &4" + player.getDisplayName() + "&c."));
    }
}