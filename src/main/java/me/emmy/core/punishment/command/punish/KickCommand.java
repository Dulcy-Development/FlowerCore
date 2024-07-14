package me.emmy.core.punishment.command.punish;

import me.emmy.core.FlowerCore;
import me.emmy.core.punishment.Punishment;
import me.emmy.core.punishment.PunishmentType;
import me.emmy.core.utils.Utils;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class KickCommand extends BaseCommand {
    @Override
    @Command(name = "kick", permission = "flower.punishment.kick", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String defaultReason = FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getString("punishments.default-reason.kick");

        String target = command.getArgs(0);
        String reason = command.length() > 1 ? command.getArgs(1) : defaultReason;
        boolean silent = command.length() > 2 && command.getArgs(2).equalsIgnoreCase("-s");

        Player targetPlayer = FlowerCore.getInstance().getServer().getPlayer(target);

        if (targetPlayer == null) {
            sender.sendMessage(CC.translate("&cPlayer not found!"));
            return;
        }

        Punishment punishment = new Punishment(targetPlayer.getName(), targetPlayer.getUniqueId(), sender.getName(), PunishmentType.KICK, reason);

        targetPlayer.kickPlayer(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("punishments.kick").replace("%punisher%", punishment.getBy()).replace("%reason%", punishment.getReason())));

        Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("punish-broadcasts.kicked").replace("%punisher%", sender.getName()).replace("%target%", target).replace("%reason%", reason)));

        if (silent) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("flowercore.staff")) {
                    player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("punish-broadcasts.kicked-silent").replace("%punisher%", sender.getName()).replace("%target%", target).replace("%reason%", reason)));
                }
            }
        } else {
            Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("punish-broadcasts.kicked").replace("%punisher%", sender.getName()).replace("%target%", target).replace("%reason%", reason)));
        }
    }
}
