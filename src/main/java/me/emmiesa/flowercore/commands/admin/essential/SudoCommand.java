package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class SudoCommand extends BaseCommand {
    @Override
    @Command(name = "sudo", permission = "flower.command.sudo", aliases = {"forcetosay"}, inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        if (command.getArgs().length < 2) {
            sender.sendMessage(CC.translate("&cUsage: /sudo (player) (message)"));
            return;
        }

        String target = command.getArgs(0);
        String arguments = String.join(" ", java.util.Arrays.copyOfRange(command.getArgs(), 1, command.getArgs().length));

        Player targetPlayer = FlowerCore.getInstance().getServer().getPlayer(target);

        if (targetPlayer == null) {
            sender.sendMessage(CC.translate(Locale.PLAYER_NOT_ONLINE).replace("%player%", target));
            return;
        }

        if (FlowerCore.getInstance().getConfig("settings.yml").getBoolean("sudo.exclude-op")) {
            if (sender instanceof Player && targetPlayer.isOp()) {
                sender.sendMessage(CC.translate("&c" + targetPlayer.getName() + " &cis too overpowered! You cannot do this action on them!"));
                return;
            /*} else if (sender.isOp() && targetPlayer.isOp()){
                sender.sendMessage(CC.translate("&c" + targetPlayer.getName() + " &cis too overpow... BUT YOU ARE STRONG ENOUGH TOO!"));
            */
            } else {
                sender.sendMessage(CC.translate("&c" + targetPlayer.getName() + " &cis too overpow... BUT YOU ARE STRONGER!"));
            }
        }

        if (command.getArgs(1).startsWith("/")) {
            sender.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("sudo.command").replace("%target%", targetPlayer.getName()).replace("%arguments%", arguments)));
        } else {
            sender.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("sudo.message").replace("%target%", targetPlayer.getName()).replace("%arguments%", arguments)));
        }

        targetPlayer.chat(arguments);
    }
}
