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
import org.bukkit.entity.Player;

import java.util.UUID;

public class BanCommand extends BaseCommand {

    @Command(name = "ban", permission = "flower.punishment.ban")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 1) {
            player.sendMessage(CC.translate("&cUsage: /ban (player) (reason) (duration) [-s]"));
            return;
        }

        String target = args.getArgs(0);
        String reason = args.length() > 1 ? args.getArgs(1) : "No reason given";
        String duration = args.length() > 2 ? args.getArgs(2) : "permanent";
        String silentornot = args.length() > 3 ? args.getArgs(3) : "";

        Player bannedBy = args.getPlayer();
        UUID playerUUID = player.getUniqueId();

        Player targetPlayer = FlowerCore.instance.getServer().getPlayer(target);
        if (target == null) {
            bannedBy.sendMessage(CC.translate("&cPlayer not found!"));
            return;
        }

        Punishment punishment = new Punishment(UUID.randomUUID(), bannedBy.getUniqueId(), PunishmentType.BAN, reason, targetPlayer.getAddress().getAddress().getHostAddress(), silentornot.equalsIgnoreCase("-s"));
        FlowerCore.getInstance().getPlayerManager().addPunishment(targetPlayer.getUniqueId(), punishment);

        String bannedmessage = CC.translate("&4&lYou've been banned from Flower Network! \n %cBanned by: &7" + bannedBy.getName() + "\n" + "&cReason: &7" + reason);
        targetPlayer.kickPlayer(bannedmessage);
        Utils.broadcastMessage(CC.translate(bannedBy.getDisplayName() + " has banned " + targetPlayer.getName() + " for " + reason + ". Duration: " + duration + (silentornot.equalsIgnoreCase("-s") ? " [Silently]" : "")));

    }
}