package me.emmiesa.flowercore.commands.admin.punishments;

import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class BanCommand extends BaseCommand {

    @Command(name = "ban", inGameOnly = false, permission = "flower.punishment.ban")
    public void onCommand(CommandArgs args) {
        if (args.length() == 0) {
            Player player = args.getPlayer();
            player.sendMessage(CC.translate("&cUsage: /ban (player) (reason) (duration) (-s / -c)"));

        } else if (args.length() == 1) {
            Player player = args.getPlayer();
            String targetName = args.getArgs(0);
            Player target = Bukkit.getServer().getPlayer(targetName);

            player.sendMessage(CC.translate("&7[!] This is where you specify the target player"));
        } else if (args.length() == 2) {
            Player player = args.getPlayer();

            player.sendMessage(CC.translate("&7[!] This is where you enter a reason"));
            //reason

        } else if (args.length() == 3) {
            Player player = args.getPlayer();

            player.sendMessage(CC.translate("&7[!] This is where you enter the duration"));
            //duration

        } else if (args.length() == 4) {
            Player player = args.getPlayer();

            player.sendMessage(CC.translate("&7[!] This is where you choose of it whether being silent or not!"));
            //-s or -c
        }
    }
}
