package me.emmiesa.flowercore.commands.admin.teleport;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeleportHereCommand extends BaseCommand {
    @Command(name = "teleporthere", permission = "flowercore.command.tphere", aliases = {"tphere", "s"})

    @Override
    public void onCommand(CommandArgs cmd) {
        Player p = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length == 0) {
            p.sendMessage("§cUsage /" + cmd.getLabel() + " <player>");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage("§cPlayer isn't online");
            return;
        }

        target.teleport(p.getLocation());
        p.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("teleport.tp-here.sender").replace("%target%", target.getDisplayName())));
        target.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("teleport.tp-here.target").replace("%player%", p.getName())));
    }
}

