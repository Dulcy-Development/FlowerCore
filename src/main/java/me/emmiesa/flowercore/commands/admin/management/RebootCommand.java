package me.emmiesa.flowercore.commands.admin.management;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 11/04/2024 - 22:35
 */

public class RebootCommand extends BaseCommand {
    private boolean rebootInProgress = false;
    private BukkitRunnable countdownTask;

    @Override
    @Command(name = "reboot", permission = "flowercore.owner", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        if (command.getArgs().length == 1 && command.getArgs(0).equalsIgnoreCase("cancel")) {
            cancelReboot(command);
        } else {
            initiateReboot(command);
        }
    }

    private void initiateReboot(CommandArgs command) {
        if (rebootInProgress) {
            command.getSender().sendMessage(CC.translate("&cA reboot is already in progress."));
            return;
        }

        if (command.getArgs().length != 1) {
            command.getSender().sendMessage(CC.translate("&cUsage: /reboot (time) &7| &c/reboot cancel"));
            return;
        }

        int countdownTime;
        try {
            countdownTime = Integer.parseInt(command.getArgs(0));
        } catch (NumberFormatException e) {
            command.getSender().sendMessage(CC.translate("&cInvalid countdown time. Please specify a valid number."));
            return;
        }

        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(CC.FLOWER_BAR_LONG_RED);
        Bukkit.broadcastMessage(CC.translate("         &cServer is rebooting in &4" + countdownTime + " &cseconds!"));
        Bukkit.broadcastMessage(CC.FLOWER_BAR_LONG_RED);
        Bukkit.broadcastMessage(" ");

        if (command.getSender().hasPermission("flowercore.owner")) {
            command.getSender().sendMessage(CC.translate("&cHey " + command.getSender().getName() + ", to cancel, type &4/reboot cancel&c. &7(Only Staff can see this message)"));
            command.getSender().sendMessage(" ");
        }

        rebootInProgress = true;

        countdownTask = new BukkitRunnable() {
            int timeLeft = countdownTime;

            @Override
            public void run() {
                if (timeLeft == 60) {
                    Bukkit.broadcastMessage(CC.translate("&cServer is going to reboot in &4" + timeLeft + " &cseconds!"));
                } else if (timeLeft == 30) {
                    Bukkit.broadcastMessage(CC.translate("&cServer is going to reboot in &4" + timeLeft + " &cseconds!"));
                } else if (timeLeft <= 10 && timeLeft > 1) {
                    Bukkit.broadcastMessage(CC.translate("&cRebooting in &4" + timeLeft + " &cseconds!"));
                } else if (timeLeft == 1) {
                    Bukkit.broadcastMessage(CC.translate("&cRebooting in &41 &csecond!"));
                } else if (timeLeft == 0) {
                    Bukkit.broadcastMessage(CC.translate("&cRebooting!"));
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.kickPlayer(CC.translate("&cServer &8(" + Locale.SERVER_NAME + "&8) &cis rebooting, this might take a moment."));
                    }
                    Bukkit.shutdown();
                    cancel();
                    rebootInProgress = false;
                    return;
                }

                timeLeft--;
            }
        };
        countdownTask.runTaskTimer(FlowerCore.getInstance(), 0L, 20L);
    }

    private void cancelReboot(CommandArgs command) {
        if (!rebootInProgress || countdownTask == null) {
            command.getSender().sendMessage(CC.translate("&cThere is no reboot progress to cancel."));
            return;
        }

        countdownTask.cancel();
        rebootInProgress = false;
        Bukkit.broadcastMessage(CC.translate("&cThe reboot was canceled by &4" + command.getSender().getName() + "&c."));
    }
}
