package me.emmy.core.punishment.command.punish;

import me.emmy.core.FlowerCore;
import me.emmy.core.profile.Profile;
import me.emmy.core.punishment.Punishment;
import me.emmy.core.punishment.PunishmentType;
import me.emmy.core.utils.Utils;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
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

        String defaultReason = FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getString("punishments.default-reason.ban");

        String targetName = command.getArgs(0);
        String reason = command.length() > 1 ? command.getArgs(1) : defaultReason;
        String duration = command.length() > 2 ? command.getArgs(2) : "permanent";
        boolean silent = command.length() > 3 && command.getArgs(3).equalsIgnoreCase("-s");

        OfflinePlayer targetPlayerOffline = Bukkit.getOfflinePlayer(targetName);
        Player targetPlayer = Bukkit.getPlayer(targetName);

        Profile profile = FlowerCore.getInstance().getProfileRepository().getProfile(targetPlayerOffline.getUniqueId());

        /*for (Punishment punishment : profile.getPunishments()) {
            if (punishment.isActive() && punishment.getType().equals(PunishmentType.BAN)) {
                sender.sendMessage(CC.translate("&4" + targetName + " &cis already banned!"));
                return;
            }
        }*/

        Punishment punishment = new Punishment(targetPlayerOffline.getName(), targetPlayerOffline.getUniqueId(), sender.getName(), PunishmentType.BAN, reason, targetPlayer != null ? targetPlayer.getAddress().getAddress().getHostAddress() : "null", false, duration, true);
        FlowerCore.getInstance().getProfileRepository().addPunishment(targetPlayerOffline.getUniqueId(), punishment);

        if (silent) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("flowercore.staff")) {
                    player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("punish-broadcasts.banned-silent").replace("%punisher%", sender.getName()).replace("%target%", targetName).replace("%reason%", reason)));
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("punish-broadcasts.banned").replace("%punisher%", sender.getName()).replace("%target%", targetName).replace("%reason%", reason)));
            Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("punish-broadcasts.banned").replace("%punisher%", sender.getName()).replace("%target%", targetName).replace("%reason%", reason)));
        }

        if (targetPlayer != null) {
            targetPlayer.kickPlayer(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("punishments.ban").replace("%punisher%", sender.getName()).replace("%reason%", reason)));
        }
    }
}
