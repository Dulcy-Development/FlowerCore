package me.emmy.core.command.admin.teleport;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.TeleportUtils;
import me.emmy.core.utils.Utils;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TeleportUpCommand extends BaseCommand {
    @Override
    @Command(name = "teleporttop", permission = "flower.cmd.top", aliases = {"top", "tptop", "ontop", "tpup"})
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length == 0) {
            player.teleport(TeleportUtils.teleportUp(player.getLocation()));
            String teleportMessage = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("teleport.tp-up");
            player.sendMessage(CC.translate(teleportMessage));
        }
    }
}
