package me.emmy.core.command.global.server;

import me.emmy.core.FlowerCore;
import me.emmy.core.locale.Locale;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class PingCommand extends BaseCommand {
    @Override
    @Command(name = "ping")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        final boolean enablePing = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getBoolean("ping.enabled");

        if (!enablePing) {
            player.sendMessage(CC.translate(getConfigMessage("ping.disabled_message")));
            return;
        }

        final boolean requirePingPermission = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getBoolean("ping.require_permission", true);

        if (requirePingPermission && !player.hasPermission(getConfigMessage("ping.permission"))) {
            player.sendMessage(CC.translate(Locale.NO_PERM));
            return;
        }

        String[] arguments = command.getArgs();

        if (command.getLabel().equalsIgnoreCase("ping")) {
            if (arguments.length == 0) {
                int ping = getPing(player);
                String pingMessage = getConfigMessage("ping.ping_self")
                        .replace("%player%", player.getName())
                        .replace("%ping%", String.valueOf(ping));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', pingMessage));
            } else if (arguments.length == 1) {
                Player target = Bukkit.getPlayer(arguments[0]);
                if (target != null) {
                    int ping = getPing(target);
                    String pingMessage = getConfigMessage("ping.ping_other")
                            .replace("%player%", target.getName())
                            .replace("%ping%", String.valueOf(ping));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', pingMessage));
                } else {
                    String notFoundMessage = getConfigMessage("ping.ping_target_not_found")
                            .replace("%player%", arguments[0]);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', notFoundMessage));
                }
            } else {
                player.sendMessage(CC.translate("&cIncorrect usage: /ping (player)"));
            }
        }
    }

    private int getPing(Player player) {
        return ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer) player).getHandle().ping;
    }

    private String getConfigMessage(String path) {
        return FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString(path, "");
    }
}