package me.emmiesa.flowercore.tips;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 04/06/2024 - 19:55
 */
public class TipsHandler {

    private final FlowerCore plugin = FlowerCore.getInstance();
    private final Random random = new Random();

    public TipsHandler() {
        if (plugin.getConfig("messages.yml").getBoolean("tips.enabled")) {
            sendTips();
        }
    }

    private void sendTips() {
        long intervalTicks = plugin.getConfig("messages.yml").getLong("tips.send-every") * 20;
        List<List<String>> tips = new ArrayList<>();

        Set<String> keys = plugin.getConfig("messages.yml").getConfigurationSection("tips.tips").getKeys(false);
        for (String key : keys) {
            List<String> tip = plugin.getConfig("messages.yml").getStringList("tips.tips." + key);
            tips.add(tip);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!tips.isEmpty()) {
                    List<String> randomTip = tips.get(random.nextInt(tips.size()));
                    randomTip.forEach(line -> {
                        String message = line
                                .replace("%bars%", plugin.getConfig("messages.yml").getString("tips.bars-format"))
                                .replace("%flowerbar%", CC.FLOWER_BAR_LONG)
                                .replace("%discord%", Locale.DISCORD)
                                .replace("%website%", Locale.WEBSITE)
                                .replace("%store%", Locale.STORE)
                                .replace("%teamspeak%", Locale.TEAMSPEAK)
                                .replace("%tiktok%", Locale.TIKTOK)
                                .replace("%twitter%", Locale.TWITTER)
                                .replace("%youtube%", Locale.YOUTUBE);
                        Utils.broadcastMessage(CC.translate(message));

                        if (plugin.getConfig("messages.yml").getBoolean("tips.console-enabled")) {
                            Bukkit.getConsoleSender().sendMessage(CC.translate(message));
                        }
                    });
                }
            }
        }.runTaskTimer(plugin, 0, intervalTicks);
    }
}
