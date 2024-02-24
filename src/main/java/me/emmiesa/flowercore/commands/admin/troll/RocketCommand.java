package me.emmiesa.flowercore.commands.admin.troll;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class RocketCommand extends BaseCommand {
    @Command(name = "rocket", inGameOnly = false, permission = "flowercore.command.rocket")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        if (args.length() > 0) {
            Player target = Bukkit.getPlayerExact(args.getArgs(0));
            if (target == null) {
                sender.sendMessage(CC.translate("&cPlayer not found."));
                return;
            }
            sender.sendMessage(CC.translate("&bYou've turned &3" + target.getDisplayName() + " &binto a rocket and now they're flying straight to the moon!"));
            target.sendMessage(CC.translate("&cYou've been turned into a rocket by &4" + args.getSender().getName() + " &cand now you're flying straight to the moon!"));
            Rocket(target);
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(CC.translate("&cUsage: /rocket (player)"));
                return;
            }
            Player player = (Player) sender;
            player.sendMessage(CC.translate("&bYou've turned &3yourself &binto a rocket and now you're flying straight to the moon!"));
            Rocket(player);
        }
    }

    private void Rocket(Player target) {
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count >= 50) {
                    this.cancel();
                    target.setGameMode(GameMode.SURVIVAL);
                    target.playSound(target.getLocation(), Sound.FIREWORK_BLAST, 1.0F, 1.0F);
                    target.playSound(target.getLocation(), Sound.FIREWORK_LARGE_BLAST, 1.0F, 1.0F);
                    target.playSound(target.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
                    target.sendMessage(CC.translate("&cOops. Too bad..."));
                } else {
                    target.setGameMode(GameMode.CREATIVE);
                    target.playSound(target.getLocation(), Sound.FIREWORK_LAUNCH, 20F, 15F);
                    target.setVelocity(new Vector(0, 1, 0).multiply(1000));
                    target.sendMessage(CC.translate("&ePff..."));
                    count++;
                }
            }
        }.runTaskTimer(FlowerCore.getInstance(), 0, 4);
    }

}
