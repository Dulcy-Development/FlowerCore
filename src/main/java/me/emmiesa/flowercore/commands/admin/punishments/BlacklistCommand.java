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

public class BlacklistCommand extends BaseCommand {

    @Command(name = "blacklist", permission = "flower.punishment.blacklist")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 1) {
            player.sendMessage(CC.translate("&cUsage: /blacklist (player) (reason) (duration) [-s]"));
            return;
        }

        String defaultreason = FlowerCore.getInstance().getConfig("settings.yml").getString("punishments.default-reason.blacklist");

        String target = args.getArgs(0);
        String reason = args.length() > 1 ? args.getArgs(1) : defaultreason;
        String duration = args.length() > 2 ? args.getArgs(2) : "permanent";
        String silentornot = args.length() > 3 ? args.getArgs(3) : "";

        Player blacklistedBy = args.getPlayer();

        Player targetPlayer = FlowerCore.getInstance().getServer().getPlayer(target);
        if (target == null) {
            blacklistedBy.sendMessage(CC.translate("&cPlayer not found!"));
            return;
        }

        Punishment punishment = new Punishment(UUID.randomUUID(), targetPlayer.getName(), blacklistedBy.getUniqueId(), PunishmentType.BLACKLIST, reason, targetPlayer.getAddress().getAddress().getHostAddress(), silentornot.equalsIgnoreCase("-s"));
        FlowerCore.getInstance().getPlayerManager().addPunishment(targetPlayer.getUniqueId(), punishment);

        targetPlayer.kickPlayer(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punishments.blacklist").replace("%punisher%", Bukkit.getOfflinePlayer(punishment.getBy()).getName()).replace("%reason%", punishment.getReason())));
        //Utils.broadcastMessage(CC.translate(blacklistedBy.getDisplayName() + " has blacklisted " + targetPlayer.getName() + " for " + reason + ". Duration: " + duration + (silentornot.equalsIgnoreCase("-s") ? " [Silently]" : "")));
        Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.blacklisted").replace("%punisher%", blacklistedBy.getDisplayName()).replace("%target%", target).replace("%reason%", reason)));
        Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.blacklisted").replace("%punisher%", blacklistedBy.getDisplayName()).replace("%target%", target).replace("%reason%", reason)));
    }
}
