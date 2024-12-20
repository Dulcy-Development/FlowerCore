package me.emmy.core.listener;

import me.emmy.core.FlowerCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class WeatherListener implements Listener {

    private final FlowerCore plugin = FlowerCore.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeatherChangeEvent(WeatherChangeEvent event) {
        if (plugin.getConfig("settings.yml").getBoolean("listeners.disable-rain") && event.toWeatherState()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onThunderChange(ThunderChangeEvent event) {
        if (plugin.getConfig("settings.yml").getBoolean("listeners.disable-rain") && event.toThunderState()) {
            event.setCancelled(true);
        }
    }
}