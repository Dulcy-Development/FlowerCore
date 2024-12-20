package me.emmy.core.command.admin.teleport;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import me.emmy.core.utils.NumberUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TeleportPositionCommand extends BaseCommand {
    @Override
    @Command(name = "teleportposition", permission = "flowercore.command.tppos", aliases = {"tp-pos", "tppos"})
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length < 3) {
            player.sendMessage("§cUsage: /tppos (x) (y) (z)");
            return;
        }
        if (NumberUtils.checkInt(args[0]) || NumberUtils.checkInt(args[1]) || NumberUtils.checkInt(args[2])) {
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
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("teleport.tp-pos")
                .replace("%x%", String.valueOf(x))
                .replace("%y%", String.valueOf(y))
                .replace("%z%", String.valueOf(z)))
        );
    }
}
