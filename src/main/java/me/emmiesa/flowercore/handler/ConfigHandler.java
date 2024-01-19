package me.emmiesa.flowercore.handler;

import lombok.Getter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class ConfigHandler {

    FlowerCore plugin = FlowerCore.getInstance();

    private File defaultConfigFile, settingsConfigFile, messagesConfigFile, commandsConfigFile, databaseConfigFile, extrasConfigFile, ranksConfigFile, permissionsConfigFile;
    private FileConfiguration defaultConfig, settingsConfig, messagesConfig, commandsConfig, databaseConfig, extrasConfig, ranksConfig, permissionsConfig;

    public ConfigHandler() {
        defaultConfigFile = new File(plugin.getDataFolder(), "config.yml");
        if (!defaultConfigFile.exists()) {
            defaultConfigFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }
        defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigFile);

        // default ^

        settingsConfigFile = new File(plugin.getDataFolder(), "settings.yml");
        if (!settingsConfigFile.exists()) {
            settingsConfigFile.getParentFile().mkdirs();
            plugin.saveResource("settings.yml", false);
        }
        settingsConfig = YamlConfiguration.loadConfiguration(settingsConfigFile);

        // settings ^

        messagesConfigFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesConfigFile.exists()) {
            messagesConfigFile.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesConfigFile);

        // messages ^

        commandsConfigFile = new File(plugin.getDataFolder(), "commands.yml");
        if (!commandsConfigFile.exists()) {
            commandsConfigFile.getParentFile().mkdirs();
            plugin.saveResource("commands.yml", false);
        }
        commandsConfig = YamlConfiguration.loadConfiguration(commandsConfigFile);

        // commands ^

        databaseConfigFile = new File(plugin.getDataFolder(), "database.yml");
        if (!databaseConfigFile.exists()) {
            databaseConfigFile.getParentFile().mkdirs();
            plugin.saveResource("database.yml", false);
        }
        databaseConfig = YamlConfiguration.loadConfiguration(databaseConfigFile);

        //database ^

        extrasConfigFile = new File(plugin.getDataFolder(), "extras.yml");
        if (!extrasConfigFile.exists()) {
            extrasConfigFile.getParentFile().mkdirs();
            plugin.saveResource("extras.yml", false);
        }
        extrasConfig = YamlConfiguration.loadConfiguration(extrasConfigFile);

        //extras ^

        ranksConfigFile = new File(plugin.getDataFolder(), "ranks.yml");
        if (!ranksConfigFile.exists()) {
            ranksConfigFile.getParentFile().mkdirs();
            plugin.saveResource("ranks.yml", false);
        }
        ranksConfig = YamlConfiguration.loadConfiguration(ranksConfigFile);

        //ranks ^

        permissionsConfigFile = new File(plugin.getDataFolder(), "permissions.yml");
        if (!permissionsConfigFile.exists()) {
            permissionsConfigFile.getParentFile().mkdirs();
            plugin.saveResource("permissions.yml", false);
        }
        permissionsConfig = YamlConfiguration.loadConfiguration(permissionsConfigFile);

        //ranks ^
    }

    public void saveConfig(File config, FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.save(config);
            fileConfiguration.load(config);
        } catch (IOException | InvalidConfigurationException e) {
            CC.sendError("Error occurred while saving config");
        }
    }

}