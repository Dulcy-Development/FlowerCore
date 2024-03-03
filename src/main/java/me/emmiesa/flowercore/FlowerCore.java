package me.emmiesa.flowercore;

import lombok.Getter;
import lombok.Setter;

import me.emmiesa.flowercore.announcements.AnnouncementManager;
import me.emmiesa.flowercore.commands.admin.essential.AlertCommand;
import me.emmiesa.flowercore.commands.admin.essential.BroadcastCommand;
import me.emmiesa.flowercore.commands.admin.essential.ClearChatCommand;
import me.emmiesa.flowercore.commands.admin.server.InstanceCommand;
import me.emmiesa.flowercore.commands.admin.essential.*;
import me.emmiesa.flowercore.commands.admin.gamemode.gmaCommand;
import me.emmiesa.flowercore.commands.admin.gamemode.gmcCommand;
import me.emmiesa.flowercore.commands.admin.gamemode.gmsCommand;
import me.emmiesa.flowercore.commands.admin.gamemode.gmspCommand;
import me.emmiesa.flowercore.commands.admin.punishments.BanCommand;
import me.emmiesa.flowercore.commands.admin.punishments.BlacklistCommand;
import me.emmiesa.flowercore.commands.admin.punishments.KickCommand;
import me.emmiesa.flowercore.commands.admin.punishments.pardon.UnbanCommand;
import me.emmiesa.flowercore.commands.admin.punishments.pardon.UnblacklistCommand;
import me.emmiesa.flowercore.commands.admin.rank.GrantCommand;
import me.emmiesa.flowercore.commands.admin.rank.RankCommand;
import me.emmiesa.flowercore.commands.admin.rank.SubCommands.*;
import me.emmiesa.flowercore.commands.admin.spawn.SetJoinLocation;
import me.emmiesa.flowercore.commands.admin.spawn.TeleportSpawnCommand;
import me.emmiesa.flowercore.commands.admin.tags.TagCommand;
import me.emmiesa.flowercore.commands.admin.teleport.*;
import me.emmiesa.flowercore.commands.admin.troll.*;
import me.emmiesa.flowercore.commands.donator.AnnounceCommand;
import me.emmiesa.flowercore.commands.global.*;
import me.emmiesa.flowercore.database.mongo.MongoManager;
import me.emmiesa.flowercore.handler.ConfigHandler;
import me.emmiesa.flowercore.listeners.ChatListener;
import me.emmiesa.flowercore.listeners.CommandListener;
import me.emmiesa.flowercore.listeners.PlayerListeners;
import me.emmiesa.flowercore.papi.ProfilePlaceholders;
import me.emmiesa.flowercore.profile.PlayerManager;
import me.emmiesa.flowercore.ranks.RanksManager;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.CommandFramework;
import me.emmiesa.flowercore.utils.menu.MenuListener;
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

    @Override
    public void onEnable() {
        instance = this;
        long start = System.currentTimeMillis();

        checkDescription();
        registerManagers();
        registerCommands();
        registerListeners();
        registerHandlers();

        CC.listRanks();

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

    private void checkDescription() {
        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] Loading..."));
        if (!getDescription().getAuthors().contains("Emmy") || !getDescription().getName().contains("FlowerCore")) {
            System.exit(0);
            Bukkit.shutdown();
        } else {
            Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] Author name is correct."));
        }
        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] Started register process..."));
    }

    private void registerManagers() {
        saveDefaultConfig();
        configHandler = new ConfigHandler();

        framework = new CommandFramework(this);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new ProfilePlaceholders().register();
        }

        this.ranksManager = new RanksManager();
        this.ranksManager.load();

        this.mongoManager = new MongoManager();
        this.mongoManager.startMongo();

        this.playerManager = new PlayerManager();

        new AnnouncementManager(this);
    }

    private void registerCommands() {
        long start = System.currentTimeMillis();

        Bukkit.getConsoleSender().sendMessage(CC.translate("Registering all commands..."));

        new FlowerCoreCommand();
        new FlyCommand();
        new gmcCommand();
        new gmsCommand();
        new gmaCommand();
        new TagCommand();
        new gmspCommand();
        new HealCommand();
        new FeedCommand();
        new PingCommand();
        new MoreCommand();
        new TrapCommand();
        new TrollCommand();
        new StoreCommand();
        new SpeedCommand();
        new CraftCommand();
        new ClearCommand();
        new TikTokCommand();
        new LaunchCommand();
        new RocketCommand();
        new CaptureCommand();
        new YouTubeCommand();
        new DiscordCommand();
        new TwitterCommand();
        new WebsiteCommand();
        new SettingsCommand();
        new ForceFlyCommand();
        new AnnounceCommand();
        new InstanceCommand();
        new TeleportCommand();
        new SetJoinLocation();
        new LocationCommand();
        new ClearChatCommand();
        new TeamSpeakCommand();
        new TeleportUpCommand();
        new TrollSilentCommand();
        new TeleportAllCommand();
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
        new RankSetColorCommand();
        new RankSetSuffixCommand();
        new RankSetPrefixCommand();
        new RankSetDisplayCommand();
        new RankSetDefaultCommand();
        new RankSetPriorityCommand();
        new RankAddPermissionsCommand();

        new BanCommand();
        new KickCommand();
        new BlacklistCommand();

        new UnbanCommand();
        new UnblacklistCommand();

        new AlertCommand(this);
        new GodModeCommand(this);
        new BroadcastCommand(this);

        if (getConfig("commands.yml").getBoolean("plugin.enabled")) {
            new PluginCommand();
        }

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate("Registered all commands in " + timeTaken + "ms."));
    }


    private void registerListeners() {
        long start = System.currentTimeMillis();

        Bukkit.getConsoleSender().sendMessage(CC.translate("Registering all listeners..."));

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new CommandListener(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate("Registered all listeners in " + timeTaken + "ms."));
    }

    private void registerHandlers() {
        long start = System.currentTimeMillis();

        Bukkit.getConsoleSender().sendMessage(CC.translate("Registering all handlers..."));

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate("Registered all handlers in " + timeTaken + "ms."));
        Bukkit.getConsoleSender().sendMessage(CC.translate("Register process was successful!"));
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
        FileConfiguration config = configHandler.getConfigByName("settings.yml");

        config.set("on-join.teleport.location.world", location.getWorld().getName());
        config.set("on-join.teleport.location.x", location.getX());
        config.set("on-join.teleport.location.y", location.getY());
        config.set("on-join.teleport.location.z", location.getZ());
        config.set("on-join.teleport.location.yaw", location.getYaw());
        config.set("on-join.teleport.location.pitch", location.getPitch());

        configHandler.saveConfig(configHandler.getConfigFileByName("settings.yml"), config);
    }

    public FileConfiguration getConfig(String fileName) {
        File configFile = new File(getDataFolder(), fileName);
        return YamlConfiguration.loadConfiguration(configFile);
    }
}
