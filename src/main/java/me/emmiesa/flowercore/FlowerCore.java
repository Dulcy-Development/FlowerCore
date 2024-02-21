package me.emmiesa.flowercore;

import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.announcements.AnnouncementManager;
import me.emmiesa.flowercore.database.mongo.MongoManager;
import me.emmiesa.flowercore.extras.scoreboard.ScoreboardLayout;
import me.emmiesa.flowercore.extras.scoreboard.assemble.Assemble;
import me.emmiesa.flowercore.extras.scoreboard.assemble.AssembleStyle;
import me.emmiesa.flowercore.handler.ConfigHandler;
//import me.emmiesa.flowercore.database.MongoHandler;
//import me.emmiesa.flowercore.database.redis.RedisHandler;
import me.emmiesa.flowercore.papi.ProfilePlaceholders;
import me.emmiesa.flowercore.plugin.register;
import me.emmiesa.flowercore.plugin.send;
import me.emmiesa.flowercore.profile.PlayerManager;
import me.emmiesa.flowercore.ranks.RanksManager;
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
    public static FlowerCore instance;
    public FileConfiguration messagesConfig, settingsConfig, commandsConfig, databaseConfig, extrasConfig, ranksConfig, permissionsConfig, placeholdersConfig;
    private Cooldown announceCooldown;
    private CommandFramework framework;
    private ConfigHandler configHandler;
    private MongoManager mongoManager;
    //private MongoHandler mongoHandler;
    //private RedisHandler redisHandler;
    private RanksManager ranksManager;
    private PlayerManager playerManager;
    private Location spawnLocation;

    public static FlowerCore get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        long start = System.currentTimeMillis();

        register.check();

        configHandler = new ConfigHandler();
        framework = new CommandFramework(this);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new ProfilePlaceholders().register();
        }

        saveDefaultConfig();
        loadSpawnLocation();
        //setupMongoRedisHandler();
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

        getConfigHandler().saveConfig(FlowerCore.instance.getConfigHandler().getSettingsConfigFile(), FlowerCore.instance.getConfigHandler().getSettingsConfig());
    }

    private void registerManagers() {
        this.ranksManager = new RanksManager();
        getRanksManager().load();

        this.mongoManager = new MongoManager();
        mongoManager.startMongo();

        this.playerManager = new PlayerManager();

        new AnnouncementManager(this);
    }

    /*private void setupMongoRedisHandler(){
        String host;
        int port;
        String password;
        boolean uri = getConfig("database.yml").getBoolean("MONGO.URI.ENABLED");
        String connectionString = getConfig("database.yml").getString("MONGO.URI.CONNECTION-STRING");
        host = getConfig("database.yml").getString("MONGO.DEFAULT.HOST");
        port = getConfig("database.yml").getInt("MONGO.DEFAULT.PORT");
        String database = getConfig("database.yml").getString("MONGO.DEFAULT.DATABASE");
        boolean authentication = getConfig("database.yml").getBoolean("MONGO.DEFAULT.AUTHENTICATION.ENABLED");
        String username = getConfig("database.yml").getString("MONGO.DEFAULT.AUTHENTICATION.USERNAME");
        password = getConfig("database.yml").getString("MONGO.DEFAULT.AUTHENTICATION.PASSWORD");
        mongoHandler = new MongoHandler(uri, connectionString, host, port, database, authentication, username, password);
        host = getConfig("database.yml").getString("REDIS.HOST");
        port = getConfig("database.yml").getInt("REDIS.PORT");
        String channel = getConfig("database.yml").getString("REDIS.CHANNEL");
        password = getConfig("database.yml").getString("REDIS.PASSWORD");
        redisHandler = new RedisHandler(host, port, channel, password);
        redisHandler.connect();
    }*/

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
