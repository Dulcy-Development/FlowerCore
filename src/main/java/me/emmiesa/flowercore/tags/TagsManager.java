package me.emmiesa.flowercore.tags;

import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 30/03/2024 - 16:56
 */

@Getter
@Setter
public class TagsManager {
    private final List<Tag> tags = new ArrayList<>();
    private final FlowerCore plugin = FlowerCore.getINSTANCE();

    public void loadConfig() {
        FileConfiguration config = plugin.getConfigHandler().getConfigByName("tags.yml");
        if (config == null || config.getConfigurationSection("tags") == null) {
            return;
        }

        for (String name : config.getConfigurationSection("tags").getKeys(false)) {
            String key = "tags." + name;
            Tag tag = new Tag(
                    name,
                    config.getString(key + ".displayName"),
                    Material.matchMaterial(config.getString(key + ".icon")),
                    config.getString(key + ".color")
            );

            tags.add(tag);
        }
    }

    public Tag getTag(String name) {
        return tags.stream()
                .filter(tag -> tag.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void removeTag(String tagName) {
        Tag tag = getTag(tagName);

        if (tag == null) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&cTag is null."));
            return;
        }

        tags.remove(tag);

        FileConfiguration config = FlowerCore.getINSTANCE().getConfigHandler().getConfigByName("tags.yml");
        File file = FlowerCore.getINSTANCE().getConfigHandler().getConfigFileByName("tags.yml");

        String key = "tags." + tagName;
        config.set(key, null);

        FlowerCore.getINSTANCE().getConfigHandler().saveConfig(file, config);
    }

    public void saveToFile() {
        tags.forEach(this::saveTag);
    }

    public void saveTag(Tag tag) {
        FileConfiguration config = plugin.getConfigHandler().getConfigByName("tags.yml");
        String key = "tags." + tag.getName();

        config.set(key + ".displayName", tag.getDisplayName());
        config.set(key + ".icon", tag.getIcon().toString());
        config.set(key + ".color", tag.getColor());

        plugin.getConfigHandler().saveConfig(plugin.getConfigHandler().getConfigFileByName("tags.yml"), config);
    }
}
