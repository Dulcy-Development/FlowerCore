package me.emmiesa.flowercore.commands.admin.teleport;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class TeleportHereCommand extends BaseCommand {
    @Command(name = "teleporthere", permission = "flowercore.command.tphere", aliases = {"tphere", "s"})

    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length == 0) {
            player.sendMessage("§cUsage /tphere (player)");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&fNo player matching &b" + args[0] + " &fis connected to this server."));
            return;
        }

        player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("teleport.tp-here.sender").replace("%target%", target.getDisplayName())));
        target.teleport(player.getLocation());
        target.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("teleport.tp-here.target").replace("%player%", player.getName())));
    }
}

