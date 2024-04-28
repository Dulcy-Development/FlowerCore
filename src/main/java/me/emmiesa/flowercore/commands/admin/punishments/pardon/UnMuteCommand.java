package me.emmiesa.flowercore.commands.admin.punishments.pardon;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.profile.Profile;
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

import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 28/04/2024 - 18:00
 */

public class UnMuteCommand extends BaseCommand {
    @Override
    @Command(name = "unmute", permission = "flower.punishment.unmute")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() < 1) {
            sender.sendMessage(CC.translate("&cUsage: /unmute (player)"));
            return;
        }

        String playerName = args.getArgs()[0];
        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(playerName);

        Profile profile = FlowerCore.getInstance().getPlayerManager().getProfile(targetPlayer.getUniqueId());

        if (profile == null) {
            sender.sendMessage(CC.translate("&4" + playerName + " &chas never joined this server before."));
            return;
        }

        if (targetPlayer.isOnline()) {
            UUID playerUUID = targetPlayer.getUniqueId();
            Player onlinePlayer = targetPlayer.getPlayer();
            Utils.broadcastMessage("&c" + targetPlayer.getName() + " &fwas unmuted by &c" + sender.getName());

            FlowerCore.getInstance().getPlayerManager().removePunishment(playerUUID, PunishmentType.MUTE, onlinePlayer.getDisplayName());
            FlowerCore.getInstance().getMongoManager().saveProfile(playerUUID);
        } else {
            UUID playerUUID = targetPlayer.getUniqueId();
            Utils.broadcastMessage("&c" + targetPlayer.getName() + " &fwas unmuted by &c" + sender.getName());

            FlowerCore.getInstance().getPlayerManager().removePunishment(playerUUID, PunishmentType.MUTE, targetPlayer.getName());
            FlowerCore.getInstance().getMongoManager().saveProfile(playerUUID);
        }
    }
}