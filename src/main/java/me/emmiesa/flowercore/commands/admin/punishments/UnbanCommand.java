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

public class UnbanCommand extends BaseCommand {
    @Override
    @Command(name = "unban", permission = "flower.punishment.ban")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 1) {
            player.sendMessage(CC.translate("&cUsage: /ban (player) (reason) (duration) [-s]"));
            return;
        }

        String target = args.getArgs(0);
        String reason = args.length() > 1 ? args.getArgs(1) : "no reason";
        String duration = args.length() > 2 ? args.getArgs(2) : "permanent";
        String silentornot = args.length() > 3 ? args.getArgs(3) : "";

        Player bannedBy = args.getPlayer();

        Player targetPlayer = FlowerCore.getInstance().getServer().getPlayer(target);
        if (target == null) {
            bannedBy.sendMessage(CC.translate("&cPlayer not found!"));
            return;
        }

        Punishment punishment = new Punishment(UUID.randomUUID(), targetPlayer == null ? target : targetPlayer.getName(), bannedBy.getUniqueId(), PunishmentType.BAN, reason, targetPlayer == null ? "No IP" : targetPlayer.getAddress().getAddress().getHostAddress(), silentornot.equalsIgnoreCase("-s"));
        FlowerCore.getInstance().getPlayerManager().removePunishment(Bukkit.getOfflinePlayer(target).getUniqueId(), punishment);

        if (targetPlayer != null) {
            targetPlayer.kickPlayer(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punishments.unban").replace("%punisher%", Bukkit.getOfflinePlayer(punishment.getBy()).getName()).replace("%reason%", punishment.getReason())));
        }
        Utils.broadcastMessage(CC.translate("&c" + FlowerCore.getInstance().getPlayerManager().getRank(bannedBy.getUniqueId()).getColor() + bannedBy.getDisplayName() + " &ahas unbanned " + FlowerCore.getInstance().getPlayerManager().getRank(targetPlayer.getUniqueId()).getColor() + target + " &afor " + reason + ". &7(duration: " + duration + "&7) &r" + (silentornot.equalsIgnoreCase("-s") ? " [Silently]" : "")));
    }
}