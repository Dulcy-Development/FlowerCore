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

    @Command(name = "ban", inGameOnly = false, permission = "flower.punishment.ban")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /ban (player) (reason) (duration) (-s / -c)"));
            return;
        }

        String target = args.getArgs(0);
        String reason = args.getArgs(1);
        String duration = args.getArgs(2);
        String silentornot = args.getArgs(3);

        Player bannedBy = args.getPlayer();
        UUID playerUUID = player.getUniqueId();

        banplayer(target, reason, duration, silentornot, bannedBy, playerUUID);
    }

    public void banplayer(String playerName, String reason, String duration, String silentornot, Player bannedBy, UUID bannedByUUID) {
        Player targetPlayer = Bukkit.getServer().getPlayer(playerName);
        if (targetPlayer == null) {
            bannedBy.sendMessage(CC.translate("&cPlayer not found!"));
            return;
        }

        //UUID targetUUID = targetPlayer.getUniqueId();
        Player target = Bukkit.getServer().getPlayer(playerName);

        Punishment punishment = new Punishment(UUID.randomUUID(), bannedBy.getUniqueId(), PunishmentType.BAN, "none", target.getAddress().getAddress().getHostAddress(), false);
        FlowerCore.getInstance().getPlayerManager().addPunishment(target.getUniqueId(), punishment);

        Utils.broadcastMessage(CC.translate(bannedBy.getDisplayName() + " has banned " + target + " because of " + reason + " for " + duration));
    }
}