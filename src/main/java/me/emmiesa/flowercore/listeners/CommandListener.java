package me.emmiesa.flowercore.listeners;

import me.emmiesa.flowercore.FlowerCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.List;

public class CommandListener implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
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
