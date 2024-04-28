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
 * Discord: dsc.gg/emmiesa
 */

public class UnbanCommand extends BaseCommand {
    @Override
    @Command(name = "unban", permission = "flower.punishment.unban", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() < 1) {
            sender.sendMessage(CC.translate("&cUsage: /unban (player)"));
            return;
        }

        String playerName = args.getArgs()[0];
        OfflinePlayer targetPlayer = offlinePlayerName(playerName); //To not make it "deprecated". Kept default in UnBlacklist command

        Profile profile = FlowerCore.getInstance().getPlayerManager().getProfile(targetPlayer.getUniqueId());

        if (profile == null) {
            sender.sendMessage(CC.translate("&4" + playerName + " &chas never joined this server before."));
            return;
        }

        if (targetPlayer.isOnline()) {
            Player onlinePlayer = targetPlayer.getPlayer();
            UUID playerUUID = onlinePlayer.getUniqueId();
            Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.un-banned").replace("%pardoner%", sender.getName()).replace("%target%", onlinePlayer.getDisplayName())));
            Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.un-banned").replace("%pardoner%", sender.getName()).replace("%target%", onlinePlayer.getDisplayName())));
            FlowerCore.getInstance().getPlayerManager().removePunishment(playerUUID, PunishmentType.BAN, onlinePlayer.getDisplayName());
            FlowerCore.getInstance().getMongoManager().saveProfile(playerUUID);
        } else {
            UUID playerUUID = targetPlayer.getUniqueId();
            Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.un-banned").replace("%pardoner%", sender.getName()).replace("%target%", targetPlayer.getName())));
            Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.un-banned").replace("%pardoner%", sender.getName()).replace("%target%", targetPlayer.getName())));
            FlowerCore.getInstance().getPlayerManager().removePunishment(playerUUID, PunishmentType.BAN, targetPlayer.getName());
            FlowerCore.getInstance().getMongoManager().saveProfile(playerUUID);
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
