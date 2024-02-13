package me.emmiesa.flowercore.listeners;

import me.emmiesa.flowercore.FlowerCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().hasPermission(FlowerCore.instance.getConfig("settings.yml").getString("vanilla-commands-blocker.perm-to-bypass"))) {
            if (FlowerCore.instance.getConfig("settings.yml").getBoolean("vanilla-commands-blocker.disable_me-say")) {
                if (event.getMessage().toLowerCase().startsWith("/me ") || event.getMessage().toLowerCase().equals("/me") ||
                        event.getMessage().toLowerCase().startsWith("/say ") || event.getMessage().toLowerCase().equals("/say")) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("Unknown command. Type \"/help\" for help.");
                }
            }
        }
    }
}