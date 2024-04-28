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
 * Discord: dsc.gg/emmiesa
 */

public class BlacklistCommand extends BaseCommand {

    @Command(name = "blacklist", permission = "flower.punishment.blacklist", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() < 1) {
            sender.sendMessage(CC.translate("&cUsage: /blacklist (player) (reason) (duration) [-s]"));
            return;
        }

        String defaultReason = FlowerCore.getInstance().getConfig("settings.yml").getString("punishments.default-reason.blacklist");

        String targetName = args.getArgs(0);
        String reason = args.length() > 1 ? args.getArgs(1) : defaultReason;
        String duration = args.length() > 2 ? args.getArgs(2) : "permanent";
        boolean silent = args.length() > 2 && args.getArgs(2).equalsIgnoreCase("-s");

        OfflinePlayer targetPlayerOffline = Bukkit.getOfflinePlayer(targetName);
        Player targetPlayer = Bukkit.getPlayer(targetName);
        String bannedByName = sender instanceof Player ? ((Player) sender).getName() : "CONSOLE";

        Punishment punishment = new Punishment(targetPlayerOffline.getName(), targetPlayerOffline.getUniqueId(), bannedByName, PunishmentType.BLACKLIST, reason, targetPlayer != null ? targetPlayer.getAddress().getAddress().getHostAddress() : "null", false, duration);
        FlowerCore.getInstance().getPlayerManager().addPunishment(targetPlayerOffline.getUniqueId(), punishment);

        if (silent) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("flowercore.staff")) {
                    player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.blacklisted-silent").replace("%punisher%", bannedByName).replace("%target%", targetName).replace("%reason%", reason)));
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.blacklisted").replace("%punisher%", bannedByName).replace("%target%", targetName).replace("%reason%", reason)));
            Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.blacklisted").replace("%punisher%", bannedByName).replace("%target%", targetName).replace("%reason%", reason)));
        }

        if (targetPlayer != null) {
            targetPlayer.kickPlayer(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punishments.blacklist").replace("%punisher%", bannedByName).replace("%reason%", reason)));
        }
    }
}
