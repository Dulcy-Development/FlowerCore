package me.emmiesa.flowercore.commands.admin.troll;

import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LaunchCommand extends BaseCommand {
    @Command(name = "launch", inGameOnly = false, permission = "flowercore.command.launch")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        if (args.length() > 0) {
            Player target = Bukkit.getPlayerExact(args.getArgs(0));
            if (target == null) {
                sender.sendMessage(CC.translate("&cPlayer not found."));
                return;
            }
            target.setVelocity(new Vector(0, 1, 0).multiply(15));
            sender.sendMessage(CC.translate("&bYou've launched &3" + target.getDisplayName() + " &binto the air!"));
            target.sendMessage(CC.translate("&cYou have been launched into the air by &4" + args.getSender().getName() + " &c!"));
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(CC.translate("&cUsage: /launch (player)"));
                return;
            }
            Player player = (Player) sender;
            player.setVelocity(new Vector(0, 1, 0).multiply(15));
            player.sendMessage(CC.translate("&bYou've launched &3yourself &binto the air!"));
        }
    }

}
