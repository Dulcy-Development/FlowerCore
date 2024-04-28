package me.emmiesa.flowercore.commands.admin.punishments;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.punishments.Punishment;
import me.emmiesa.flowercore.punishments.PunishmentType;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 28/04/2024 - 17:50
 */

public class MuteCommand extends BaseCommand {
    @Override
    @Command(name = "mute", permission = "flower.punishment.mute")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() < 1) {
            sender.sendMessage(CC.translate("&cUsage: /mute (player) (reason) (duration) [-s]"));
            return;
        }

        String defaultReason = FlowerCore.getInstance().getConfig("settings.yml").getString("punishments.default-reason.mute");

        String targetName = args.getArgs(0);
        String reason = args.length() > 1 ? args.getArgs(1) : defaultReason;
        String duration = args.length() > 2 ? args.getArgs(2) : "permanent";
        boolean silent = args.length() > 2 && args.getArgs(2).equalsIgnoreCase("-s");

        OfflinePlayer targetPlayerOffline = Bukkit.getOfflinePlayer(targetName);
        Player targetPlayer = Bukkit.getPlayer(targetName);
        String mutedByName = sender instanceof Player ? ((Player) sender).getName() : "CONSOLE";

        Punishment punishment = new Punishment(targetPlayerOffline.getName(), targetPlayerOffline.getUniqueId(), mutedByName, PunishmentType.MUTE, reason, targetPlayer != null ? targetPlayer.getAddress().getAddress().getHostAddress() : "null", false, duration);
        FlowerCore.getInstance().getPlayerManager().addPunishment(targetPlayerOffline.getUniqueId(), punishment);

        if (silent) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("flowercore.staff")) {
                    player.sendMessage(CC.translate("&7[SILENT] &c" + targetName + " &fhas been muted by &c" + mutedByName));
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&c" + targetName + " &fhas been muted by &c" + mutedByName));
            Utils.broadcastMessage(CC.translate("&c" + targetName + " &fhas been muted by &c" + mutedByName));
        }
    }
}
