package me.emmiesa.flowercore.handler;

import lombok.Getter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ConfigHandler {

    private final FlowerCore plugin = FlowerCore.getInstance();

    private final Map<String, File> configFiles = new HashMap<>();
    private final Map<String, FileConfiguration> fileConfigurations = new HashMap<>();

    private final String[] configFileNames = {
            "config.yml", "settings.yml", "messages.yml", "commands.yml", "database.yml", "extras.yml", "ranks.yml", "placeholders.yml"
    };

    public ConfigHandler() {
        for (String fileName : configFileNames) {
            loadConfig(fileName);
        }
    }

    private void loadConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        configFiles.put(fileName, configFile);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        fileConfigurations.put(fileName, config);
    }

    public void saveConfigs() {
        for (Map.Entry<String, FileConfiguration> entry : fileConfigurations.entrySet()) {
            String fileName = entry.getKey();
            FileConfiguration config = entry.getValue();
            File configFile = configFiles.get(fileName);
            saveConfig(configFile, config);
        }
    }

    public void saveConfig(File configFile, FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.save(configFile);
            fileConfiguration.load(configFile);
        } catch (Exception e) {
            CC.sendError("Error occurred while saving config: " + configFile.getName());
        }
    }

    public FileConfiguration getConfigByName(String fileName) {
        return fileConfigurations.get(fileName);
    }

    public File getConfigFileByName(String fileName) {
        return configFiles.get(fileName);
    }
}