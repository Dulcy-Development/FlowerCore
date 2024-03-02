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
        if (FlowerCore.getInstance().getConfig("settings.yml").getBoolean("version-command.enabled")) {
            if (event.getMessage().toLowerCase().startsWith("/version") || event.getMessage().toLowerCase().startsWith("/ver") || event.getMessage().toLowerCase().startsWith("/bukkit:ver") || event.getMessage().toLowerCase().startsWith("/bukkit:version") || event.getMessage().toLowerCase().startsWith("/bukkit:about") || event.getMessage().toLowerCase().startsWith("/about") || event.getMessage().equalsIgnoreCase("/?")) {
                event.setCancelled(true);
                for (String message : FlowerCore.getInstance().getConfig("settings.yml").getStringList("version-command.message")) {
                    event.getPlayer().sendMessage(CC.translate(message)
                            .replace("%player%", event.getPlayer().getDisplayName())
                            .replace("%spigot%", Bukkit.getServer().getName())
                            .replace("%version%", Bukkit.getServer().getVersion()));
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
