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

        String defaultreason = FlowerCore.getInstance().getConfig("settings.yml").getString("punishments.default-reason.ban");

        String target = args.getArgs(0);
        String reason = args.length() > 1 ? args.getArgs(1) : defaultreason;
        String duration = args.length() > 2 ? args.getArgs(2) : "permanent";
        String silentornot = args.length() > 3 ? args.getArgs(3) : "";

        Player bannedBy = args.getPlayer();

        Player targetPlayer = FlowerCore.getInstance().getServer().getPlayer(target);
        if (target == null) {
            bannedBy.sendMessage(CC.translate("&cPlayer not found!"));
            return;
        }

        Punishment punishment = new Punishment(UUID.randomUUID(), targetPlayer == null ? target : targetPlayer.getName(), bannedBy.getUniqueId(), PunishmentType.BAN, reason, targetPlayer == null ? "No IP" : targetPlayer.getAddress().getAddress().getHostAddress(), silentornot.equalsIgnoreCase("-s"));
        FlowerCore.getInstance().getPlayerManager().addPunishment(Bukkit.getOfflinePlayer(target).getUniqueId(), punishment);

        if (targetPlayer != null) {
            targetPlayer.kickPlayer(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punishments.ban").replace("%punisher%", Bukkit.getOfflinePlayer(punishment.getBy()).getName()).replace("%reason%", punishment.getReason())));
        }
        Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.banned").replace("%punisher%", bannedBy.getDisplayName()).replace("%target%", target).replace("%reason%", reason)));
        Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.banned").replace("%punisher%", bannedBy.getDisplayName()).replace("%target%", target).replace("%reason%", reason)));
        //Utils.broadcastMessage(CC.translate("&c" + FlowerCore.getInstance().getPlayerManager().getRank(bannedBy.getUniqueId()).getColor() + bannedBy.getDisplayName() + " &ahas banned " + FlowerCore.getInstance().getPlayerManager().getRank(targetPlayer.getUniqueId()).getColor() + target + " &afor " + reason + ". &7(duration: " + duration + "&7) &r" + (silentornot.equalsIgnoreCase("-s") ? " [Silently]" : "")));
    }
}