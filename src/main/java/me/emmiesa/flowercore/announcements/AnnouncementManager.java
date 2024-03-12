package me.emmiesa.flowercore.announcements;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AnnouncementManager {

    private final FlowerCore plugin;
    private final Random random = new Random();

    public AnnouncementManager(FlowerCore plugin) {
        this.plugin = plugin;
        if (plugin.getConfig("messages.yml").getBoolean("announcements.enabled")) {
            sendAnnounce();
        }
    }

    private void sendAnnounce() {
        long intervalTicks = plugin.getConfig("messages.yml").getLong("announcements.send-every") * 20;
        List<List<String>> allAnnouncements = new ArrayList<>();

        Set<String> keys = plugin.getConfig("messages.yml").getConfigurationSection("announcements.announcements").getKeys(false);
        for (String key : keys) {
            List<String> announcement = plugin.getConfig("messages.yml").getStringList("announcements.announcements." + key);
            allAnnouncements.add(announcement);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!allAnnouncements.isEmpty()) {
                    List<String> selectedAnnouncement = allAnnouncements.get(random.nextInt(allAnnouncements.size()));
                    selectedAnnouncement.forEach(line -> {
                        String message = line
                                .replace("%bars%", plugin.getConfig("messages.yml").getString("announcements.bars-format"))
                                .replace("%flowerbar%", CC.FLOWER_BAR_LONG)
                                .replace("%discord%", Locale.DISCORD)
                                .replace("%website%", Locale.WEBSITE)
                                .replace("%store%", Locale.STORE)
                                .replace("%teamspeak%", Locale.TEAMSPEAK)
                                .replace("%tiktok%", Locale.TIKTOK)
                                .replace("%twitter%", Locale.TWITTER)
                                .replace("%youtube%", Locale.YOUTUBE);
                        Utils.broadcastMessage(CC.translate(message));

                        if (plugin.getConfig("messages.yml").getBoolean("announcements.console-enabled")) {
                            Bukkit.getConsoleSender().sendMessage(CC.translate(message));
                        }
                    });
                }
            }
        }.runTaskTimer(plugin, 0, intervalTicks);
    }
}