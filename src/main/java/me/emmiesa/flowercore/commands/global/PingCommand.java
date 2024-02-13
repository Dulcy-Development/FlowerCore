package me.emmiesa.flowercore.commands.global;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PingCommand extends BaseCommand {

    @Command(name = "ping")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        final boolean enablePing = FlowerCore.getInstance().getConfig("messages.yml").getBoolean("ping.enabled");

        if (!enablePing) {
            player.sendMessage(CC.translate(getConfigMessage("ping.disabled_message")));
            return;
        }

        final boolean requirePingPermission = FlowerCore.getInstance().getConfig("messages.yml").getBoolean("ping.require_permission", true);

        if (requirePingPermission && !player.hasPermission(getConfigMessage("ping.permission"))) {
            player.sendMessage(CC.translate(Locale.NO_PERM));
            return;
        }

        String[] arguments = args.getArgs();

        if (args.getLabel().equalsIgnoreCase("ping")) {
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
        return FlowerCore.getInstance().getConfig("messages.yml").getString(path, "");
    }
}