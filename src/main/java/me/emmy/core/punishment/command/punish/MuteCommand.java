package me.emmy.core.punishment.command.punish;

import me.emmy.core.FlowerCore;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.CommandArgs;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.punishment.Punishment;
import me.emmy.core.punishment.PunishmentType;
import me.emmy.core.utils.Utils;
import me.emmy.core.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 28/04/2024 - 17:50
 */
public class MuteCommand extends BaseCommand {
    @Override
    @Command(name = "mute", permission = "flower.punishment.mute")
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(CC.translate("&cUsage: /mute (player) (reason) (duration) [-s]"));
            return;
        }

        String defaultReason = FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getString("punishments.default-reason.mute");

        String targetName = command.getArgs(0);
        String reason = command.length() > 1 ? command.getArgs(1) : defaultReason;
        String duration = command.length() > 2 ? command.getArgs(2) : "permanent";
        boolean silent = command.length() > 3 && command.getArgs(3).equalsIgnoreCase("-s");

        OfflinePlayer targetPlayerOffline = Bukkit.getOfflinePlayer(targetName);
        Player targetPlayer = Bukkit.getPlayer(targetName);

        Punishment punishment = new Punishment(targetPlayerOffline.getName(), targetPlayerOffline.getUniqueId(), sender.getName(), PunishmentType.MUTE, reason, targetPlayer != null ? targetPlayer.getAddress().getAddress().getHostAddress() : "null", false, duration, true);
        FlowerCore.getInstance().getProfileRepository().addPunishment(targetPlayerOffline.getUniqueId(), punishment);

        if (silent) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("flowercore.staff")) {
                    player.sendMessage(CC.translate("&7[SILENT] &c" + targetName + " &fhas been muted by &c" + sender.getName()));
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&c" + targetName + " &fhas been muted by &c" + sender.getName()));
            Utils.broadcastMessage(CC.translate("&c" + targetName + " &fhas been muted by &c" + sender.getName()));
        }
    }
}
