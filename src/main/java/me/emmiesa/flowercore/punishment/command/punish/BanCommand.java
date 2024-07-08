package me.emmiesa.flowercore.punishment.command.punish;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.profile.Profile;
import me.emmiesa.flowercore.punishment.Punishment;
import me.emmiesa.flowercore.punishment.PunishmentType;
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
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class BanCommand extends BaseCommand {

    @Command(name = "ban", permission = "flower.punishment.ban", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        if (command.length() < 1) {
            sender.sendMessage(CC.translate("&cUsage: /ban (player) (reason) (duration) [-s]"));
            return;
        }

        String defaultReason = FlowerCore.getInstance().getConfig("settings.yml").getString("punishments.default-reason.ban");

        String targetName = command.getArgs(0);
        String reason = command.length() > 1 ? command.getArgs(1) : defaultReason;
        String duration = command.length() > 2 ? command.getArgs(2) : "permanent";
        boolean silent = command.length() > 3 && command.getArgs(3).equalsIgnoreCase("-s");

        OfflinePlayer targetPlayerOffline = Bukkit.getOfflinePlayer(targetName);
        Player targetPlayer = Bukkit.getPlayer(targetName);
        String bannedByName = sender instanceof Player ? ((Player) sender).getName() : "CONSOLE";

        Profile profile = FlowerCore.getInstance().getProfileRepository().getProfile(targetPlayerOffline.getUniqueId());

        /*for (Punishment punishment : profile.getPunishments()) {
            if (punishment.isActive() && punishment.getType().equals(PunishmentType.BAN)) {
                sender.sendMessage(CC.translate("&4" + targetName + " &cis already banned!"));
                return;
            }
        }*/

        Punishment punishment = new Punishment(targetPlayerOffline.getName(), targetPlayerOffline.getUniqueId(), bannedByName, PunishmentType.BAN, reason, targetPlayer != null ? targetPlayer.getAddress().getAddress().getHostAddress() : "null", false, duration, true);
        FlowerCore.getInstance().getProfileRepository().addPunishment(targetPlayerOffline.getUniqueId(), punishment);

        if (silent) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("flowercore.staff")) {
                    player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.banned-silent").replace("%punisher%", bannedByName).replace("%target%", targetName).replace("%reason%", reason)));
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.banned").replace("%punisher%", bannedByName).replace("%target%", targetName).replace("%reason%", reason)));
            Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.banned").replace("%punisher%", bannedByName).replace("%target%", targetName).replace("%reason%", reason)));
        }

        if (targetPlayer != null) {
            targetPlayer.kickPlayer(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punishments.ban").replace("%punisher%", bannedByName).replace("%reason%", reason)));
        }
    }
}
