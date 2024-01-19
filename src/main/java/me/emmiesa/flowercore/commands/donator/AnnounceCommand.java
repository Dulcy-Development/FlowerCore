package me.emmiesa.flowercore.commands.donator;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import me.emmiesa.flowercore.utils.others.Cooldown;
import org.bukkit.entity.Player;

/*
 * FrozedClub
 * Project: FrozedSG
 */

public class AnnounceCommand extends BaseCommand {

    @Command(name = "announce", permission = "flowercore.command.announce")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!FlowerCore.instance.getConfig("settings.yml").getBoolean("announce.enabled")) {
            player.sendMessage(CC.translate(FlowerCore.instance.getConfig("settings.yml").getString("announce.disabled")));
            return;
        }

        if (!player.hasPermission("flowercore.bypass.cooldown")) {
            if (FlowerCore.getInstance().getAnnounceCooldown() != null && !FlowerCore.instance.getAnnounceCooldown().hasExpired()) {
                player.sendMessage(CC.translate("&cYou must wait " + FlowerCore.instance.getAnnounceCooldown().getMiliSecondsLeft() + " seconds since the game has already been announced."));
                return;
            }
        }

        String format = FlowerCore.instance.getConfig("settings.yml").getString("announce.format");
        format = format.replace("%server_name%", FlowerCore.instance.getConfig("settings.yml").getString("server-name"));
        format = format.replace("%player_display_name%", player.getDisplayName());
        format = format.replace("%player_name%", player.getName());

        String cmd = FlowerCore.instance.getConfig("settings.yml").getString("announce.command");

        if (!FlowerCore.instance.getConfig("settings.yml").getBoolean("announce.bungee")) {
            player.sendMessage(CC.translate(FlowerCore.instance.getConfig("settings.yml").getString("announce.announced-message")));
            Utils.broadcastMessage(format);
        } else {
            player.sendMessage(CC.translate(FlowerCore.instance.getConfig("settings.yml").getString("announce.announced-message")));
            Utils.sendGlobalClickableMessage(player, format, cmd);
        }

        FlowerCore.instance.setAnnounceCooldown(new Cooldown(FlowerCore.instance.getConfig("settings.yml").getInt("announce.cooldown")));
    }
}