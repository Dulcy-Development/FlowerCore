package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationCommand extends BaseCommand {

    @Command(name = "location", permission = "flowercore.command.location", aliases = {"loc", "whereami"})
    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        Location loc = player.getLocation();

        String message = FlowerCore.getInstance().getConfig("messages.yml").getString("current-location");
        message = message.replace("{world}", loc.getWorld().getName())
                .replace("{x}", String.format("%.2f", loc.getX()))
                .replace("{y}", String.format("%.2f", loc.getY()))
                .replace("{z}", String.format("%.2f", loc.getZ()))
                .replace("{yaw}", String.format("%.2f", loc.getYaw()))
                .replace("{pitch}", String.format("%.2f", loc.getPitch()));

        player.sendMessage(CC.translate(message));
    }
}
