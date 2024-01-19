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
    @Command(name = "teleportposition", permission = "flowercore.command.tppos", aliases = {"tp-pos"})

    @Override
    public void onCommand(CommandArgs cmd) {
        Player p = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length < 3) {
            p.sendMessage("§cUsage: /" + cmd.getLabel() + " <x> <y> <z>");
            return;
        }
        if (!NumberUtils.checkInt(args[0]) || !NumberUtils.checkInt(args[1]) || !NumberUtils.checkInt(args[2])) {
            p.sendMessage("§ePlease specify a number");
            return;
        }

        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        Location loc = new Location(p.getWorld(), x, y, z);
        if (x > 30000000 || x < -30000000 || y > 30000000 || y < -30000000 || z > 30000000 || z < -30000000) {
            p.sendMessage("§eMax value is 30000000/-30000000");
            return;
        }

        p.teleport(loc);
        p.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("teleport.tp-pos")
                .replace("%x%", String.valueOf(x))
                .replace("%y%", String.valueOf(y))
                .replace("%z%", String.valueOf(z)))
        );
    }
}
