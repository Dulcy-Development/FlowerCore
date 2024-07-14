package me.emmy.core.command.admin.essential;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class LocationCommand extends BaseCommand {
    @Override
    @Command(name = "location", permission = "flower.command.location", aliases = {"loc", "whereami"})
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        Location loc = player.getLocation();

        String message = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("current-location");
        message = message.replace("{world}", loc.getWorld().getName())
                .replace("{x}", String.format("%.2f", loc.getX()))
                .replace("{y}", String.format("%.2f", loc.getY()))
                .replace("{z}", String.format("%.2f", loc.getZ()))
                .replace("{yaw}", String.format("%.2f", loc.getYaw()))
                .replace("{pitch}", String.format("%.2f", loc.getPitch()));

        player.sendMessage(CC.translate(message));
        Bukkit.getConsoleSender().sendMessage(CC.translate("\n&3(" + player.getName() + ") &r" + message));
    }
}
