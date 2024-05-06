package me.emmiesa.flowercore.ranks;

import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.tags.Tag;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lrxh
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

@Getter
@Setter
public class RanksManager {
    private final List<Rank> ranks = new ArrayList<>();
    private final FlowerCore plugin = FlowerCore.getInstance();
    private Rank defaultRank;

    public void load() {
        FileConfiguration config = plugin.getConfig("ranks.yml");
        if (config.getConfigurationSection("ranks") == null) {
            return;
        }

        for (String name : config.getConfigurationSection("ranks").getKeys(false)) {
            String key = "ranks." + name;
            Rank rank = new Rank(
                    name,
                    config.getString(key + ".displayName"),
                    Material.matchMaterial(config.getString(key + ".icon")),
                    config.getString(key + ".prefix"),
                    config.getString(key + ".color"),
                    config.getString(key + ".suffix"),
                    config.getInt(key + ".priority"),
                    config.getBoolean(key + ".default"),
                    config.getBoolean(key + ".staff"),
                    config.getStringList(key + ".permissions")
            );

            ranks.add(rank);

            if (rank.isDefaultRank()) {
                setDefaultRank(rank);
            }
        }
    }

    public Rank getRank(String name) {
        return ranks.stream()
                .filter(rank -> rank.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void saveToFile() {
        ranks.forEach(this::saveRank);
    }

    public void saveRank(Rank rank) {
        FileConfiguration config = getPlugin().getConfigHandler().getConfigByName("ranks.yml");
        String key = "ranks." + rank.getName();

        config.set(key + ".displayName", rank.getDisplayName());
        config.set(key + ".icon", rank.getIcon().toString());
        config.set(key + ".prefix", rank.getPrefix());
        config.set(key + ".color", rank.getColor());
        config.set(key + ".suffix", rank.getSuffix());
        config.set(key + ".priority", rank.getPriority());
        config.set(key + ".default", rank.isDefaultRank());
        config.set(key + ".staff", rank.isStaff());
        config.set(key + ".permissions", rank.getPermissions());

        getPlugin().getConfigHandler().saveConfig(getPlugin().getConfigHandler().getConfigFileByName("ranks.yml"), config);
    }

    public void removeRank(String rankName) {
        Rank rank = getRank(rankName);

        if (rank == null) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&cTag is null."));
            return;
        }

        ranks.remove(rank);

        FileConfiguration config = FlowerCore.getInstance().getConfigHandler().getConfigByName("ranks.yml");
        File file = FlowerCore.getInstance().getConfigHandler().getConfigFileByName("ranks.yml");

        String key = "ranks." + rankName;
        config.set(key, null);

        FlowerCore.getInstance().getConfigHandler().saveConfig(file, config);
    }

    public Rank getPlayerRank(Player player) {
        return FlowerCore.getInstance().getPlayerManager().getRank(player.getUniqueId());
    }
}