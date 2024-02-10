package me.emmiesa.flowercore;

import me.emmiesa.flowercore.commands.admin.administration.AlertCommand;
import me.emmiesa.flowercore.commands.admin.administration.BroadcastCommand;
import me.emmiesa.flowercore.commands.admin.administration.ClearChatCommand;
import me.emmiesa.flowercore.commands.admin.administration.InstanceCommand;
import me.emmiesa.flowercore.commands.admin.essential.*;
import me.emmiesa.flowercore.commands.admin.gamemode.gmaCommand;
import me.emmiesa.flowercore.commands.admin.gamemode.gmcCommand;
import me.emmiesa.flowercore.commands.admin.gamemode.gmsCommand;
import me.emmiesa.flowercore.commands.admin.gamemode.gmspCommand;
import me.emmiesa.flowercore.commands.admin.rank.GrantCommand;
import me.emmiesa.flowercore.commands.admin.rank.RankCommand;
import me.emmiesa.flowercore.commands.admin.rank.SubCommands.*;
import me.emmiesa.flowercore.commands.admin.spawn.SetJoinLocation;
import me.emmiesa.flowercore.commands.admin.spawn.TeleportSpawnCommand;
import me.emmiesa.flowercore.commands.admin.teleport.TeleportCommand;
import me.emmiesa.flowercore.commands.admin.teleport.TeleportHereCommand;
import me.emmiesa.flowercore.commands.admin.teleport.TeleportPositionCommand;
import me.emmiesa.flowercore.commands.admin.teleport.TeleportUpCommand;
import me.emmiesa.flowercore.commands.donator.AnnounceCommand;
import me.emmiesa.flowercore.commands.global.*;
import me.emmiesa.flowercore.database.MongoManager;
import me.emmiesa.flowercore.extras.scoreboard.assemble.Assemble;
import me.emmiesa.flowercore.extras.scoreboard.assemble.AssembleStyle;
import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.extras.scoreboard.ScoreboardLayout;
import me.emmiesa.flowercore.listeners.CommandListener;
import me.emmiesa.flowercore.papi.ProfilePlaceholders;
import me.emmiesa.flowercore.profile.PlayerManager;
import me.emmiesa.flowercore.ranks.RanksManager;
import me.emmiesa.flowercore.plugin.send;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.menu.MenuListener;
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

    public FileConfiguration messagesConfig, settingsConfig, commandsConfig, databaseConfig, extrasConfig, ranksConfig, permissionsConfig, placeholdersConfig;

    @Override
    public void onEnable() {
        instance = this;
        long start = System.currentTimeMillis();

        check();
        saveDefaultConfig();
        loadSpawnLocation();
        registerManagers();
        registerScoreboard();
        registerCommands();
        registerListeners();
        registerHandlers();
        done();

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
        placeholdersConfig = getConfig("placeholder.yml");
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

    public FileConfiguration getConfig(String fileName) {
        File configFile = new File(getDataFolder(), fileName);
        return YamlConfiguration.loadConfiguration(configFile);
    }

    private void registerCommands() {
        long start = System.currentTimeMillis();

        Bukkit.getConsoleSender().sendMessage(CC.translate(Locale.PREFIX + " | Registering all commands..."));

        new FlowerCoreCommand();

        new FlyCommand();
        new gmcCommand();
        new gmsCommand();
        new gmaCommand();
        new gmspCommand();
        new HealCommand();
        new FeedCommand();
        new PingCommand();
        new MoreCommand();
        new TrollCommand();
        new StoreCommand();
        new SpeedCommand();
        new TikTokCommand();
        new YouTubeCommand();
        new DiscordCommand();
        new TwitterCommand();
        new WebsiteCommand();
        new SettingsCommand();
        new ForceFlyCommand();
        new TeleportCommand();
        new AnnounceCommand();
        new InstanceCommand();
        new TeleportCommand();
        new SetJoinLocation();
        new ClearChatCommand();
        new TeamSpeakCommand();
        new TeleportUpCommand();
        new SpeedResetCommand();
        new TeleportHereCommand();
        new TeleportSpawnCommand();
        new TrollEverybodyCommand();
        new TeleportPositionCommand();

        new RankCommand();
        new GrantCommand();
        new RankListCommand();
        new RankSaveCommand();
        new RankCreateCommand();
        new RankSetIconCommand();
        new RankSetStaffCommand();
        new RankSetSuffixCommand();
        new RankSetPrefixCommand();
        new RankSetDisplayCommand();
        new RankSetDefaultCommand();
        new RankSetPriorityCommand();
        new RankAddPermissionsCommand();

        new AlertCommand(this);
        new GodModeCommand(this);
        new BroadcastCommand();

        if (getConfig("commands.yml").getBoolean("plugin.enabled")) {
            new PluginCommand();
        }

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] | Registered all commands in " + timeTaken + "ms."));
    }

    private void registerHandlers() {
        long start = System.currentTimeMillis();

        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] | Registering all handlers..."));

        configHandler = new ConfigHandler();
        framework = new CommandFramework(this);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new ProfilePlaceholders().register();
        }

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] | Registered all handlers in " + timeTaken + "ms."));
    }

    private void registerListeners() {
        long start = System.currentTimeMillis();

        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] | Registering all listeners..."));

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new me.emmiesa.flowercore.listeners.PlayerListeners(this), this);
        getServer().getPluginManager().registerEvents(new me.emmiesa.flowercore.listeners.ChatListener(), this);
        getServer().getPluginManager().registerEvents(new CommandListener(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] | Registered all listeners in " + timeTaken + "ms."));

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

    private void check() {
        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] Loading..."));
        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] Checking plugin.yml..."));
        if (!FlowerCore.instance.getDescription().getAuthors().contains("Emmiesa") || !FlowerCore.instance.getDescription().getName().contains("FlowerCore")) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&4[FlowerCore] WRONG AUTHOR/PLUGIN NAME! Shutting the server down..."));
            System.exit(0);
            Bukkit.shutdown();
        } else {
            Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] No changes detected"));
        }
        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] Started register process..."));
    }

    private void done() {
        Bukkit.getConsoleSender().sendMessage(CC.translate(" "));
        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] Register was successful."));
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
}
