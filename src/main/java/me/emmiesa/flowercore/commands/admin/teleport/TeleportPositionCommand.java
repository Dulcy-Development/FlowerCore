package me.emmiesa.flowercore.commands.admin.teleport;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import me.emmiesa.flowercore.utils.others.NumberUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportPositionCommand extends BaseCommand {
    @Command(name = "teleportposition", permission = "flowercore.command.tppos", aliases = {"tp-pos", "tppos"})

    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length < 3) {
            player.sendMessage("§cUsage: /tppos (x) (y) (z)");
            return;
        }
        if (!NumberUtils.checkInt(args[0]) || !NumberUtils.checkInt(args[1]) || !NumberUtils.checkInt(args[2])) {
            player.sendMessage(CC.translate("&cInvalid value. Please specify numbers."));
            return;
        }

        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        Location loc = new Location(player.getWorld(), x, y, z);
        if (x > 30000000 || x < -30000000 || y > 30000000 || y < -30000000 || z > 30000000 || z < -30000000) {
            player.sendMessage(CC.translate("&cYou can't teleport further than 30000000/-30000000."));
            return;
        }

        player.teleport(loc);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("teleport.tp-pos")
                .replace("%x%", String.valueOf(x))
                .replace("%y%", String.valueOf(y))
                .replace("%z%", String.valueOf(z)))
        );
    }
}
