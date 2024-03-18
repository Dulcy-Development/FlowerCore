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

public class TeleportCommand extends BaseCommand {

    @Command(name = "teleport", permission = "flowercore.command.teleport", aliases = {"tp", "tpto", "teleportto"})
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();
        if (args.length == 0) {
            player.sendMessage(CC.translate("§cUsage: /tp (player)"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&fNo player matching &b" + args[0] + " &fis connected to this server."));
            return;
        }

        player.teleport(target.getLocation());
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("teleport.tp").replace("%target%", target.getDisplayName())));
    }
}