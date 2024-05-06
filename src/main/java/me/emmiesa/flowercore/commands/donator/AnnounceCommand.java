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

    @Command(name = "announce", permission = "flowercore.command.announce")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (!FlowerCore.getINSTANCE().getConfig("settings.yml").getBoolean("announce.enabled")) {
            player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("settings.yml").getString("announce.disabled")));
            return;
        }

        if (!player.hasPermission("flowercore.bypass.cooldown")) {
            if (FlowerCore.getINSTANCE().getAnnounceCooldown() != null && !FlowerCore.getINSTANCE().getAnnounceCooldown().hasExpired()) {
                player.sendMessage(CC.translate("&cYou must wait " + FlowerCore.getINSTANCE().getAnnounceCooldown().getMiliSecondsLeft() + " seconds since the game has already been announced."));
                return;
            }
        }

        String format = FlowerCore.getINSTANCE().getConfig("settings.yml").getString("announce.format");
        format = format.replace("%server_name%", FlowerCore.getINSTANCE().getConfig("settings.yml").getString("server-name"));
        format = format.replace("%prefix%", FlowerCore.getINSTANCE().getPlayerManager().getRank(playerUUID).getPrefix());
        format = format.replace("%player-display-name%", FlowerCore.getINSTANCE().getPlayerManager().getPlayerRankColor(playerUUID));
        format = format.replace("%player_name%", player.getName());

        String cmd = FlowerCore.getINSTANCE().getConfig("settings.yml").getString("announce.command");

        if (!FlowerCore.getINSTANCE().getConfig("settings.yml").getBoolean("announce.bungee")) {
            player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("settings.yml").getString("announce.announced-message")));
            Utils.broadcastMessage(format);
        } else {
            player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("settings.yml").getString("announce.announced-message")));
            Utils.sendGlobalClickableMessage(player, format, cmd);
        }

        FlowerCore.getINSTANCE().setAnnounceCooldown(new Cooldown(FlowerCore.getINSTANCE().getConfig("settings.yml").getInt("announce.cooldown")));
    }
}
