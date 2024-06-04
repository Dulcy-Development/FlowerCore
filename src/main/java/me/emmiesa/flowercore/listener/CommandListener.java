package me.emmiesa.flowercore.listener;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class CommandListener implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (FlowerCore.getInstance().getConfig("settings.yml").getBoolean("plugins-command.enabled")) {
            if (!event.getPlayer().hasPermission(FlowerCore.getInstance().getConfig("settings.yml").getString("plugins-command.perm-to-bypass"))) {
                String[] parts = event.getMessage().toLowerCase().trim().split("\\s+");
                if (parts[0].equals("/version") || parts[0].equals("/ver") || parts[0].equals("/bukkit:ver") || parts[0].equals("/bukkit:version") || parts[0].equals("/bukkit:about") || parts[0].equals("/about") || parts[0].equals("/?")) {
                    event.setCancelled(true);
                    for (String msg : FlowerCore.getInstance().getConfig("settings.yml").getStringList("version-command.message")) {
                        event.getPlayer().sendMessage(CC.translate(msg)
                                .replace("%player%", event.getPlayer().getDisplayName())
                                .replace("%spigot%", Bukkit.getServer().getName())
                                .replace("%version%", Bukkit.getServer().getVersion()));
                    }
                }
            }
        }

        if (FlowerCore.getInstance().getConfig("settings.yml").getBoolean("plugins-command.enabled")) {
            if (!event.getPlayer().hasPermission(FlowerCore.getInstance().getConfig("settings.yml").getString("plugins-command.perm-to-bypass"))) {
                if (event.getMessage().equals("/plugins") || event.getMessage().equals("/pl") || event.getMessage().equals("/bukkit:pl") || event.getMessage().equals("/bukkit:plugins")) {
                    event.setCancelled(true);
                    for (String message : FlowerCore.getInstance().getConfig("settings.yml").getStringList("plugins-command.message")) {
                        event.getPlayer().sendMessage(CC.translate(message)
                                .replace("%player%", event.getPlayer().getDisplayName()));
                    }
                }
            }
        }

        if (FlowerCore.getInstance().getConfig("settings.yml").getBoolean("bungee-command.enabled")) {
            if (!event.getPlayer().hasPermission(FlowerCore.getInstance().getConfig("settings.yml").getString("bungee-command.perm-to-bypass"))) {
                if (event.getMessage().equals("/bungee") || event.getMessage().equals("/bungeecord") || event.getMessage().equals("/flamecord") || event.getMessage().equals("/flame") || event.getMessage().equals("/proxy")) {
                    event.setCancelled(true);
                    for (String message : FlowerCore.getInstance().getConfig("settings.yml").getStringList("bungee-command.message")) {
                        event.getPlayer().sendMessage(CC.translate(message)
                                .replace("%player%", event.getPlayer().getDisplayName()));
                    }
                }
            }
        }

        if (!event.getPlayer().hasPermission(FlowerCore.getInstance().getConfig("settings.yml").getString("vanilla-commands-blocker.perm-to-bypass"))) {
            List<String> blockedCommands = FlowerCore.getInstance().getConfig("settings.yml").getStringList("vanilla-commands-blocker.disable_commands");

            for (String command : blockedCommands) {
                String formattedCommand = command.startsWith("/") ? command : "/" + command;
                if (event.getMessage().toLowerCase().startsWith(formattedCommand.toLowerCase())) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("Unknown command. Type \"/help\" for help.");
                    break;
                }
            }
        }
    }
}