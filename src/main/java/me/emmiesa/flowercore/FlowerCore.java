package me.emmiesa.flowercore;

import me.emmiesa.flowercore.database.MongoManager;
import me.emmiesa.flowercore.extras.scoreboard.assemble.Assemble;
import me.emmiesa.flowercore.extras.scoreboard.assemble.AssembleStyle;
import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.extras.scoreboard.ScoreboardLayout;
import me.emmiesa.flowercore.plugin.register;
import me.emmiesa.flowercore.profile.PlayerManager;
import me.emmiesa.flowercore.ranks.RanksManager;
import me.emmiesa.flowercore.plugin.send;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import me.emmiesa.flowercore.handler.ConfigHandler;
import me.emmiesa.flowercore.utils.others.Cooldown;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import me.emmiesa.flowercore.utils.command.CommandFramework;

import java.io.File;

@Getter @Setter
public class FlowerCore extends JavaPlugin {

    @Getter public static FlowerCore instance;

    private Cooldown announceCooldown;
    private CommandFramework framework;
    private ConfigHandler configHandler;
    private MongoManager mongoManager;
    private RanksManager ranksManager;
    private PlayerManager playerManager;
    private Location spawnLocation;

    public FileConfiguration messagesConfig, settingsConfig, commandsConfig, databaseConfig, extrasConfig, ranksConfig, permissionsConfig;

    @Override
    public void onEnable() {
        instance = this;
        long start = System.currentTimeMillis();

        register.check();

        configHandler = new ConfigHandler();
        framework = new CommandFramework(this);

        saveDefaultConfig();
        loadSpawnLocation();
        registerManagers();
        registerScoreboard();
        register.commands();
        register.handlers();
        register.listeners();
        register.done();


        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        send.on(timeTaken);
    }

    @Override
    public void onDisable() {
        getRanksManager().saveToFile();
        getMongoManager().close();
        getMongoManager().saveAllPlayerData();
        send.off();
    }

    public static FlowerCore get() {
        return instance;
    }

    public void reloadAllConfigs() {
        commandsConfig = getConfig("commands.yml");
        databaseConfig = getConfig("database.yml");
        extrasConfig = getConfig("extras.yml");
        messagesConfig = getConfig("messages.yml");
        settingsConfig = getConfig("settings.yml");
        ranksConfig = getConfig("ranks.yml");
        permissionsConfig = getConfig("permissions.yml");
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

        getConfigHandler().saveConfig(FlowerCore.instance.getConfigHandler().getSettingsConfigFile(), FlowerCore.instance.getConfigHandler().getSettingsConfig());
    }

    private void registerManagers() {
        this.ranksManager = new RanksManager();
        getRanksManager().load();

        this.mongoManager = new MongoManager();
        mongoManager.startMongo();

        this.playerManager = new PlayerManager();
    }

    private void registerScoreboard() {
        if (FlowerCore.instance.getConfig("extras.yml").getBoolean("scoreboard.enabled")) {
            Assemble assemble = new Assemble(this, new ScoreboardLayout());
            assemble.setAssembleStyle(AssembleStyle.MODERN);
            assemble.setTicks(2);
        }
    }

    public FileConfiguration getConfig(String fileName) {
        File configFile = new File(getDataFolder(), fileName);
        return YamlConfiguration.loadConfiguration(configFile);
    }
}
