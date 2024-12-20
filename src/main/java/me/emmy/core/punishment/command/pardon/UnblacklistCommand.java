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
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 23/04/2024 - 12:04
 */
public class UnblacklistCommand extends BaseCommand {
    @Override
    @Command(name = "unblacklist", permission = "flower.punishment.unblacklist", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        if (command.length() < 1) {
            sender.sendMessage(CC.translate("&cUsage: /unblacklist (player)"));
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
            if (!punishment.isActive() && punishment.getType().equals(PunishmentType.BLACKLIST)) {
                sender.sendMessage(CC.translate("&4" + targetPlayer.getName()+ " &cis already un-blacklisted!"));
                return;
            }
        }*/

        if (targetPlayer.isOnline()) {
            UUID playerUUID = targetPlayer.getUniqueId();
            Player onlinePlayer = targetPlayer.getPlayer();

            Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("punish-broadcasts.un-blacklisted").replace("%pardoner%", sender.getName()).replace("%target%", onlinePlayer.getDisplayName())));
            Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("punish-broadcasts.un-blacklisted").replace("%pardoner%", sender.getName()).replace("%target%", onlinePlayer.getDisplayName())));

            for (Punishment punishment : profile.getPunishments()) {
                if (punishment.isActive() && punishment.getType().equals(PunishmentType.BLACKLIST)) {
                    punishment.setActive(false);
                }
            }
            FlowerCore.getInstance().getMongoService().saveProfile(playerUUID);
        } else {
            UUID playerUUID = targetPlayer.getUniqueId();

            Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("punish-broadcasts.un-blacklisted").replace("%pardoner%", sender.getName()).replace("%target%", targetPlayer.getName())));
            Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("punish-broadcasts.un-blacklisted").replace("%pardoner%", sender.getName()).replace("%target%", targetPlayer.getName())));

            for (Punishment punishment : profile.getPunishments()) {
                if (punishment.isActive() && punishment.getType().equals(PunishmentType.BLACKLIST)) {
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
