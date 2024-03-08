package me.emmiesa.flowercore.listeners;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.List;

public class CommandListener implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (FlowerCore.getInstance().getConfig("commands.yml").getBoolean("version-command.enabled")) {
            if (event.getMessage().equals("/version") || event.getMessage().equals("/ver") || event.getMessage().equals("/bukkit:ver") || event.getMessage().equals("/bukkit:version") || event.getMessage().equals("/bukkit:about") || event.getMessage().equals("/about") || event.getMessage().equals("/?")) {
                event.setCancelled(true);
                for (String message : FlowerCore.getInstance().getConfig("commands.yml").getStringList("version-command.message")) {
                    event.getPlayer().sendMessage(CC.translate(message)
                            .replace("%player%", event.getPlayer().getDisplayName())
                            .replace("%spigot%", Bukkit.getServer().getName())
                            .replace("%version%", Bukkit.getServer().getVersion()));
                }
            }
        }

        if (FlowerCore.getInstance().getConfig("commands.yml").getBoolean("plugins-command.enabled")) {
            if (event.getMessage().equals("/plugins") || event.getMessage().equals("/pl") || event.getMessage().equals("/bukkit:pl") || event.getMessage().equals("/bukkit:plugins")) {
                event.setCancelled(true);
                for (String message : FlowerCore.getInstance().getConfig("commands.yml").getStringList("plugins-command.message")) {
                    event.getPlayer().sendMessage(CC.translate(message)
                            .replace("%player%", event.getPlayer().getDisplayName()));
                }
            }
        }

        if (FlowerCore.getInstance().getConfig("commands.yml").getBoolean("bungee-command.enabled")) {
            if (event.getMessage().equals("/bungee") || event.getMessage().toLowerCase().startsWith("/bungeecord") || event.getMessage().equals("/flamecord") || event.getMessage().equals("/flame") || event.getMessage().equals("/proxy")) {
                event.setCancelled(true);
                for (String message : FlowerCore.getInstance().getConfig("commands.yml").getStringList("bungee-command.message")) {
                    event.getPlayer().sendMessage(CC.translate(message)
                            .replace("%player%", event.getPlayer().getDisplayName()));
                            //.replace("%spigot%", Bukkit.getServer().getName())
                            //.replace("%version%", Bukkit.getServer().getVersion()))
                }
            }
        }

        if (!event.getPlayer().hasPermission(FlowerCore.getInstance().getConfig("commands.yml").getString("vanilla-commands-blocker.perm-to-bypass"))) {

            List<String> blockedCommands = FlowerCore.getInstance().getConfig("commands.yml").getStringList("vanilla-commands-blocker.disable_commands");

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