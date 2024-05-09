package me.emmiesa.flowercore.commands.donator;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import me.emmiesa.flowercore.utils.Cooldown;
import org.bukkit.entity.Player;

import java.util.UUID;


/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 * Credit: FrozedUHCMeetup (for Utils)
 */

public class AnnounceCommand extends BaseCommand {

    @Command(name = "announce", permission = "donator.announce")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (!FlowerCore.getInstance().getConfig("settings.yml").getBoolean("announce.enabled")) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("settings.yml").getString("announce.disabled")));
            return;
        }

        if (!player.hasPermission("donator.announce.bypasscooldown")) {
            if (FlowerCore.getInstance().getAnnounceCooldown() != null && !FlowerCore.getInstance().getAnnounceCooldown().hasExpired()) {
                player.sendMessage(CC.translate("&cYou must wait " + FlowerCore.getInstance().getAnnounceCooldown().getMiliSecondsLeft() + " seconds since the game has already been announced."));
                return;
            }
        }

        String format = FlowerCore.getInstance().getConfig("settings.yml").getString("announce.format");
        format = format.replace("%server_name%", FlowerCore.getInstance().getConfig("settings.yml").getString("server-name"));
        format = format.replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix());
        format = format.replace("%player-display-name%", FlowerCore.getInstance().getPlayerManager().getPlayerRankColor(playerUUID));
        format = format.replace("%player_name%", player.getName());

        String cmd = FlowerCore.getInstance().getConfig("settings.yml").getString("announce.command");

        if (!FlowerCore.getInstance().getConfig("settings.yml").getBoolean("announce.bungee")) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("settings.yml").getString("announce.announced-message")));
            Utils.broadcastMessage(format);
        } else {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("settings.yml").getString("announce.announced-message")));
            Utils.sendGlobalClickableMessage(player, format, cmd);
        }

        FlowerCore.getInstance().setAnnounceCooldown(new Cooldown(FlowerCore.getInstance().getConfig("settings.yml").getInt("announce.cooldown")));
    }
}
