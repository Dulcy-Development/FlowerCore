package me.emmiesa.flowercore.punishment.command.punish;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.punishment.Punishment;
import me.emmiesa.flowercore.punishment.PunishmentType;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class KickCommand extends BaseCommand {
    @Override
    @Command(name = "kick", permission = "flower.punishment.kick")
    public void onCommand(CommandArgs args) {

        String defaultReason = FlowerCore.getInstance().getConfig("settings.yml").getString("punishments.default-reason.kick");

        String target = args.getArgs(0);
        String reason = args.length() > 1 ? args.getArgs(1) : defaultReason;
        boolean silent = args.length() > 2 && args.getArgs(2).equalsIgnoreCase("-s");

        Player kickedBy = args.getPlayer();

        Player targetPlayer = FlowerCore.getInstance().getServer().getPlayer(target);

        if (targetPlayer == null) {
            kickedBy.sendMessage(CC.translate("&cPlayer not found!"));
            return;
        }

        Punishment punishment = new Punishment(targetPlayer.getName(), targetPlayer.getUniqueId(), kickedBy.getDisplayName(), PunishmentType.KICK, reason);

        targetPlayer.kickPlayer(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punishments.kick").replace("%punisher%", punishment.getByString()).replace("%reason%", punishment.getReason())));

        Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.kicked").replace("%punisher%", kickedBy.getDisplayName()).replace("%target%", target).replace("%reason%", reason)));

        if (silent) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("flowercore.staff")) {
                    player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.kicked-silent").replace("%punisher%", kickedBy.getDisplayName()).replace("%target%", target).replace("%reason%", reason)));
                }
            }
        } else {
            Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punish-broadcasts.kicked").replace("%punisher%", kickedBy.getDisplayName()).replace("%target%", target).replace("%reason%", reason)));
        }
    }
}
