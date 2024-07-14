package me.emmy.core.punishment.command.pardon;

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

import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 28/04/2024 - 18:00
 */
public class UnMuteCommand extends BaseCommand {
    @Override
    @Command(name = "unmute", permission = "flower.punishment.unmute")
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        if (command.length() < 1) {
            sender.sendMessage(CC.translate("&cUsage: /unmute (player)"));
            return;
        }

        String playerName = command.getArgs()[0];
        OfflinePlayer targetPlayer = offlinePlayerName(playerName); //To not make it "deprecated". Kept default in UnBlacklist command

        Profile profile = FlowerCore.getInstance().getProfileRepository().getProfile(targetPlayer.getUniqueId());

        if (profile == null) {
            sender.sendMessage(CC.translate("&4" + playerName + " &chas never joined this server before."));
            return;
        }

        /*for (Punishment punishment : profile.getPunishments()) {
            if (!punishment.isActive() && punishment.getType().equals(PunishmentType.MUTE)) {
                sender.sendMessage(CC.translate("&4" + targetPlayer.getName()+ " &cis already un-muted!"));
                return;
            }
        }*/

        if (targetPlayer.isOnline()) {
            UUID playerUUID = targetPlayer.getUniqueId();
            Utils.broadcastMessage("&c" + targetPlayer.getName() + " &fwas unmuted by &c" + sender.getName());

            for (Punishment punishment : profile.getPunishments()) {
                if (punishment.isActive() && punishment.getType().equals(PunishmentType.MUTE)) {
                    punishment.setActive(false);
                }
            }
            FlowerCore.getInstance().getMongoService().saveProfile(playerUUID);
        } else {
            UUID playerUUID = targetPlayer.getUniqueId();
            Utils.broadcastMessage("&c" + targetPlayer.getName() + " &fwas unmuted by &c" + sender.getName());

            for (Punishment punishment : profile.getPunishments()) {
                if (punishment.isActive() && punishment.getType().equals(PunishmentType.MUTE)) {
                    punishment.setActive(false);
                }
            }
            FlowerCore.getInstance().getMongoService().saveProfile(playerUUID);
        }
    }

    public OfflinePlayer offlinePlayerName(String playerName) {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName() != null && player.getName().equalsIgnoreCase(playerName)) {
                return player;
            }
        }
        return null;
    }
}