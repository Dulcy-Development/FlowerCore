package me.emmiesa.flowercore.commands.admin.teleport;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class TeleportUpCommand extends BaseCommand {
    @Command(name = "teleporttop", permission = "flower.cmd.top", aliases = {"top", "tptop", "ontop", "tpup"})
    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length == 0) {
            player.teleport(Utils.tptop(player.getLocation()));
            String teleportMessage = FlowerCore.instance.getConfig("messages.yml").getString("teleport.tp-up");
            player.sendMessage(CC.translate(teleportMessage));
        }
    }
}
