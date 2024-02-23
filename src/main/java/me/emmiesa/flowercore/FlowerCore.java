package me.emmiesa.flowercore;

import lombok.Getter;
import lombok.Setter;

import me.emmiesa.flowercore.announcements.AnnouncementManager;
import me.emmiesa.flowercore.database.mongo.MongoManager;
import me.emmiesa.flowercore.handler.ConfigHandler;
import me.emmiesa.flowercore.papi.ProfilePlaceholders;
import me.emmiesa.flowercore.plugin.Register;
import me.emmiesa.flowercore.profile.PlayerManager;
import me.emmiesa.flowercore.ranks.RanksManager;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.CommandFramework;
import me.emmiesa.flowercore.utils.others.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
@Setter
public class FlowerCore extends JavaPlugin {

    @Getter
    private static FlowerCore instance;

    private FileConfiguration messagesConfig, settingsConfig, commandsConfig, databaseConfig, extrasConfig, ranksConfig, permissionsConfig, placeholdersConfig;
    private Cooldown announceCooldown;
    private CommandFramework framework;
    private ConfigHandler configHandler;
    private MongoManager mongoManager;
    private RanksManager ranksManager;
    private PlayerManager playerManager;
    private Location spawnLocation;
    private Register register;

    @Override
    public void onEnable() {
        instance = this;
        long start = System.currentTimeMillis();

        configHandler = new ConfigHandler();
        saveDefaultConfig();
        loadSpawnLocation();
        registerManagers();

        framework = new CommandFramework(this);
        register = new Register();
        register.check();
        register.commands();
        register.handlers();
        register.listeners();
        register.done();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new ProfilePlaceholders().register();
        }

        long end = System.currentTimeMillis();
        long timeTaken = end - start;
        CC.on(timeTaken);
    }

    @Override
    public void onDisable() {
        ranksManager.saveToFile();
        mongoManager.close();
        mongoManager.saveAllPlayerData();
        CC.off();
    }

    public void reloadAllConfigs() {
        commandsConfig = getConfig("commands.yml");
        databaseConfig = getConfig("database.yml");
        extrasConfig = getConfig("extras.yml");
        messagesConfig = getConfig("messages.yml");
        settingsConfig = getConfig("settings.yml");
        ranksConfig = getConfig("ranks.yml");
        permissionsConfig = getConfig("permissions.yml");
        placeholdersConfig = getConfig("placeholder.yml");
    }

    private void loadSpawnLocation() {
        FileConfiguration config = getConfig("settings.yml");
        boolean enableSpawnTeleport = config.getBoolean("on-join.teleport.enabled");

        if (enableSpawnTeleport) {
            World world = Bukkit.getWorld(config.getString("on-join.teleport.location.world"));
            double x = config.getDouble("on-join.teleport.location.x");
            double y = config.getDouble("on-join.teleport.location.y");
            double z = config.getDouble("on-join.teleport.location.z");
            float yaw = (float) config.getDouble("on-join.teleport.location.yaw");
            float pitch = (float) config.getDouble("on-join.teleport.location.pitch");

            spawnLocation = new Location(world, x, y, z, yaw, pitch);
        }
    }

    public void setSpawnLocation(Location location) {
        this.spawnLocation = location;

        getConfig("settings.yml").set("on-join.teleport.location.world", location.getWorld().getName());
        getConfig("settings.yml").set("on-join.teleport.location.x", location.getX());
        getConfig("settings.yml").set("on-join.teleport.location.y", location.getY());
        getConfig("settings.yml").set("on-join.teleport.location.z", location.getZ());
        getConfig("settings.yml").set("on-join.teleport.location.yaw", location.getYaw());
        getConfig("settings.yml").set("on-join.teleport.location.pitch", location.getPitch());

        configHandler.saveConfig(configHandler.getConfigFileByName("settings.yml"), configHandler.getConfigByName("settings.yml"));
    }

    private void registerManagers() {
        this.ranksManager = new RanksManager();
        getRanksManager().load();

        this.mongoManager = new MongoManager();
        mongoManager.startMongo();

        this.playerManager = new PlayerManager();

        new AnnouncementManager(this);
    }

    public FileConfiguration getConfig(String fileName) {
        File configFile = new File(getDataFolder(), fileName);
        return YamlConfiguration.loadConfiguration(configFile);
    }
}
