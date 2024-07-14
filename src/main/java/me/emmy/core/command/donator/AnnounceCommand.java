package me.emmy.core.command.donator;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.AnnounceUtil;
import me.emmy.core.utils.Utils;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import me.emmy.core.utils.Cooldown;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 08/02/2024 - 11:18
 * @credit FCD
 */
public class AnnounceCommand extends BaseCommand {
    @Override
    @Command(name = "announce", permission = "donator.announce")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (!FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getBoolean("announce.enabled")) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getString("announce.disabled")));
            return;
        }

        if (!player.hasPermission("donator.announce.bypasscooldown")) {
            if (FlowerCore.getInstance().getCooldown() != null && !FlowerCore.getInstance().getCooldown().hasExpired()) {
                player.sendMessage(CC.translate("&cYou must wait " + FlowerCore.getInstance().getCooldown().getMiliSecondsLeft() + " seconds since the game has already been announced."));
                return;
            }
        }

        String format = FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getString("announce.format");
        format = format.replace("%server_name%", FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getString("server-name"));
        format = format.replace("%prefix%", FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).getPrefix());
        format = format.replace("%player-display-name%", FlowerCore.getInstance().getProfileRepository().getPlayerRankColor(playerUUID));
        format = format.replace("%player_name%", player.getName());

        String cmd = FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getString("announce.command");

        if (!FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getBoolean("announce.bungee")) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getString("announce.announced-message")));
            Utils.broadcastMessage(format);
        } else {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getString("announce.announced-message")));
            AnnounceUtil.sendGlobalClickableMessage(player, format, cmd);
        }

        FlowerCore.getInstance().setCooldown(new Cooldown(FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getInt("announce.cooldown")));
    }
}
