package me.emmy.core.spawn;

import lombok.Getter;
import me.emmy.core.FlowerCore;
import me.emmy.core.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 04/06/2024 - 20:14
 */
@Getter
public class SpawnHandler {

    private Location spawnLocation;

    /**
     * Load the spawn location from the setting.yml file
     */
    public void loadSpawnLocation() {
        Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getPrefix() + "&fSpawn location has been loaded."));
        FileConfiguration config = FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml");

        if (!config.getBoolean("on-join.teleport.enabled")) {
            return;
        }

        World world = Bukkit.getWorld(config.getString("on-join.teleport.location.world"));
        double x = config.getDouble("on-join.teleport.location.x");
        double y = config.getDouble("on-join.teleport.location.y");
        double z = config.getDouble("on-join.teleport.location.z");
        float yaw = (float) config.getDouble("on-join.teleport.location.yaw");
        float pitch = (float) config.getDouble("on-join.teleport.location.pitch");

        spawnLocation = new Location(world, x, y, z, yaw, pitch);
    }

    /**
     * Set the spawn location
     *
     * @param location Location to set
     */
    public void setSpawnLocation(Location location) {
        this.spawnLocation = location;
        FileConfiguration config = FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml");

        config.set("on-join.teleport.location.world", location.getWorld().getName());
        config.set("on-join.teleport.location.x", location.getX());
        config.set("on-join.teleport.location.y", location.getY());
        config.set("on-join.teleport.location.z", location.getZ());
        config.set("on-join.teleport.location.yaw", location.getYaw());
        config.set("on-join.teleport.location.pitch", location.getPitch());

        FlowerCore.getInstance().getConfigHandler().saveConfig(FlowerCore.getInstance().getConfigHandler().getConfigFile("settings.yml"), config);
    }
}
