package me.emmiesa.flowercore.commands.admin.punishments.pardon;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.punishments.PunishmentType;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class UnblacklistCommand extends BaseCommand {
    @Override
    @Command(name = "unblacklist", permission = "flower.punishment.unblacklist", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() < 1) {
            sender.sendMessage(CC.translate("&cUsage: /unblacklist (player)"));
            return;
        }

        String targetName = args.getArgs(0);

        UUID playerUUID = findUUIDByName(targetName);

        if (playerUUID == null) {
            sender.sendMessage(CC.translate("&4" + targetName + " &chas never joined this server before or the username is invalid."));
            return;
        }

        Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.un-blacklisted").replace("%pardoner%", sender.getName()).replace("%target%", targetName)));
        Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.un-blacklisted").replace("%pardoner%", sender.getName()).replace("%target%", targetName)));
        //Utils.broadcastMessage(CC.translate("&4" + targetName + " &4was unblacklisted by &4" + sender.getName() + "&4."));
        FlowerCore.getInstance().getPlayerManager().removePunishment(playerUUID, PunishmentType.BLACKLIST, targetName);
        FlowerCore.getInstance().getMongoManager().saveProfile(playerUUID);
    }

    public UUID findUUIDByName(String playerName) {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName() != null && player.getName().equalsIgnoreCase(playerName)) {
                return player.getUniqueId();
            }
        }
        return null;
    }
}