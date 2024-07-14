package me.emmy.core.tip;

import me.emmy.core.FlowerCore;
import me.emmy.core.locale.Locale;
import me.emmy.core.utils.Utils;
import me.emmy.core.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 04/06/2024 - 19:55
 */
public class TipsHandler {

    private final FlowerCore plugin = FlowerCore.getInstance();
    private final Random random = new Random();

    /**
     * Constructor for the TipsHandler
     */
    public TipsHandler() {
        if (plugin.getConfig("messages.yml").getBoolean("tip.enabled")) {
            sendTips();
        }
    }

    /**
     * Sends tip to the server by running a BukkitRunnable
     */
    private void sendTips() {
        long intervalTicks = plugin.getConfig("messages.yml").getLong("tip.send-every") * 20;
        List<List<String>> tips = new ArrayList<>();

        Set<String> keys = plugin.getConfig("messages.yml").getConfigurationSection("tip.tip").getKeys(false);
        for (String key : keys) {
            List<String> tip = plugin.getConfig("messages.yml").getStringList("tip.tip." + key);
            tips.add(tip);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!tips.isEmpty()) {
                    List<String> randomTip = tips.get(random.nextInt(tips.size()));
                    randomTip.forEach(line -> {
                        String message = line
                                .replace("%bars%", plugin.getConfig("messages.yml").getString("tip.bars-format"))
                                .replace("%flowerbar%", CC.FLOWER_BAR_LONG)
                                .replace("%discord%", Locale.DISCORD)
                                .replace("%website%", Locale.WEBSITE)
                                .replace("%store%", Locale.STORE)
                                .replace("%teamspeak%", Locale.TEAMSPEAK)
                                .replace("%tiktok%", Locale.TIKTOK)
                                .replace("%twitter%", Locale.TWITTER)
                                .replace("%youtube%", Locale.YOUTUBE);
                        Utils.broadcastMessage(CC.translate(message));

                        if (plugin.getConfig("messages.yml").getBoolean("tip.console-enabled")) {
                            Bukkit.getConsoleSender().sendMessage(CC.translate(message));
                        }
                    });
                }
            }
        }.runTaskTimer(plugin, 0, intervalTicks);
    }
}
