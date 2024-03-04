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

public class KickCommand extends BaseCommand {
    @Override
    @Command(name = "kick", permission = "flower.punishment.kick")
    public void onCommand(CommandArgs args) {

        String defaultreason = FlowerCore.getInstance().getConfig("settings.yml").getString("punishments.default-reason.kick");

        String target = args.getArgs(0);
        String reason = args.length() > 1 ? args.getArgs(1) : defaultreason;
        String silentornot = args.length() > 2 ? args.getArgs(2) : "";

        Player kickedBy = args.getPlayer();

        Player targetPlayer = FlowerCore.getInstance().getServer().getPlayer(target);
        if (target == null) {
            kickedBy.sendMessage(CC.translate("&cPlayer not found!"));
            return;
        }

        Punishment punishment = new Punishment(UUID.randomUUID(), targetPlayer == null ? target : targetPlayer.getName(), kickedBy.getUniqueId(), PunishmentType.KICK, reason, targetPlayer == null ? "No IP" : targetPlayer.getAddress().getAddress().getHostAddress(), silentornot.equalsIgnoreCase("-s"));

        if (targetPlayer != null) {
            targetPlayer.kickPlayer(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punishments.kick").replace("%punisher%", Bukkit.getOfflinePlayer(punishment.getBy()).getName()).replace("%reason%", punishment.getReason())));

            Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.kicked").replace("%punisher%", kickedBy.getDisplayName()).replace("%target%", target).replace("%reason%", reason)));
            Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.kicked").replace("%punisher%", kickedBy.getDisplayName()).replace("%target%", target).replace("%reason%", reason)));
        }
    }
}
