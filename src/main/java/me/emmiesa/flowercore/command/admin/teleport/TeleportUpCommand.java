package me.emmiesa.flowercore.command.admin.teleport;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
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
            player.teleport(Utils.tptop(player.getLocation()));
            String teleportMessage = FlowerCore.getInstance().getConfig("messages.yml").getString("teleport.tp-up");
            player.sendMessage(CC.translate(teleportMessage));
        }
    }
}
