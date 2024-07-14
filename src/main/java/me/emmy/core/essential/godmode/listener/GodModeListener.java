package me.emmy.core.essential.godmode.listener;

import me.emmy.core.FlowerCore;
import me.emmy.core.essential.godmode.GodModeMemory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 14/07/2024 - 15:24
 */
public class GodModeListener implements Listener {

    private final GodModeMemory godModeMemory = FlowerCore.getInstance().getGodModeMemory();

    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (godModeMemory.isGodModeEnabled(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (godModeMemory.isGodModeEnabled(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (godModeMemory.isGodModeEnabled(player)) {
                event.setCancelled(true);
            }
        }
    }
}


