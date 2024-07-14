package me.emmy.core.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 14/07/2024 - 14:52
 */
@UtilityClass
public class TeleportUtils {

    /**
     * Get the highest block at a location
     *
     * @param loc The location
     * @return The highest block at the location
     */
    public Location teleportUp(Location loc) {
        return new Location(loc.getWorld(), loc.getX(), loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()), loc.getZ(), loc.getYaw(), loc.getPitch());
    }
}
