package me.emmiesa.flowercore;

import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.announcement.AnnouncementManager;
import me.emmiesa.flowercore.commands.FlowerCoreCommand;
import me.emmiesa.flowercore.commands.admin.essential.*;
import me.emmiesa.flowercore.commands.admin.gamemode.gmaCommand;
import me.emmiesa.flowercore.commands.admin.gamemode.gmcCommand;
import me.emmiesa.flowercore.commands.admin.gamemode.gmsCommand;
import me.emmiesa.flowercore.commands.admin.gamemode.gmspCommand;
import me.emmiesa.flowercore.commands.admin.management.*;
import me.emmiesa.flowercore.commands.admin.punishments.BanCommand;
import me.emmiesa.flowercore.commands.admin.punishments.BlacklistCommand;
import me.emmiesa.flowercore.commands.admin.punishments.KickCommand;
import me.emmiesa.flowercore.commands.admin.punishments.MuteCommand;
import me.emmiesa.flowercore.commands.admin.punishments.pardon.UnMuteCommand;
import me.emmiesa.flowercore.commands.admin.punishments.pardon.UnbanCommand;
import me.emmiesa.flowercore.commands.admin.punishments.pardon.UnblacklistCommand;
import me.emmiesa.flowercore.commands.admin.rank.GrantCommand;
import me.emmiesa.flowercore.commands.admin.rank.RankCommand;
import me.emmiesa.flowercore.commands.admin.rank.subcommands.*;
import me.emmiesa.flowercore.commands.admin.server.InstanceCommand;
import me.emmiesa.flowercore.commands.admin.spawn.SetJoinLocation;
import me.emmiesa.flowercore.commands.admin.spawn.TeleportSpawnCommand;
import me.emmiesa.flowercore.commands.admin.tags.TagAdminCommand;
import me.emmiesa.flowercore.commands.admin.tags.TagCommand;
import me.emmiesa.flowercore.commands.admin.tags.subcommands.*;
import me.emmiesa.flowercore.commands.admin.teleport.*;
import me.emmiesa.flowercore.commands.admin.troll.*;
import me.emmiesa.flowercore.commands.donator.AnnounceCommand;
import me.emmiesa.flowercore.commands.global.conversation.MessageCommand;
import me.emmiesa.flowercore.commands.global.conversation.ReplyCommand;
import me.emmiesa.flowercore.commands.global.menus.NewsCommand;
import me.emmiesa.flowercore.commands.global.menus.SettingsCommand;
import me.emmiesa.flowercore.commands.global.server.JoinCommand;
import me.emmiesa.flowercore.commands.global.server.PingCommand;
import me.emmiesa.flowercore.commands.global.socials.*;
import me.emmiesa.flowercore.commands.global.toggle.ToggleGlobalChatCommand;
import me.emmiesa.flowercore.commands.global.toggle.TogglePrivateMessageSounds;
import me.emmiesa.flowercore.commands.global.toggle.TogglePrivateMessagesCommand;
import me.emmiesa.flowercore.commands.global.worldtime.DayCommand;
import me.emmiesa.flowercore.commands.global.worldtime.NightCommand;
import me.emmiesa.flowercore.commands.global.worldtime.SunsetCommand;
import me.emmiesa.flowercore.database.mongo.MongoManager;
import me.emmiesa.flowercore.handler.ConfigHandler;
import me.emmiesa.flowercore.handler.ConversationHandler;
import me.emmiesa.flowercore.listeners.ChatListener;
import me.emmiesa.flowercore.listeners.CommandListener;
import me.emmiesa.flowercore.listeners.PlayerListener;
import me.emmiesa.flowercore.placeholder.Placeholder;
import me.emmiesa.flowercore.profile.PlayerManager;
import me.emmiesa.flowercore.ranks.RanksManager;
import me.emmiesa.flowercore.tags.TagsManager;
import me.emmiesa.flowercore.utils.Cooldown;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.CommandFramework;
import me.emmiesa.flowercore.utils.menu.MenuListener;
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
    private Cooldown announceCooldown;
    private CommandFramework framework;
    private ConversationHandler conversationHandler;
    private ConfigHandler configHandler;
    private MongoManager mongoManager;
    private RanksManager ranksManager;
    private TagsManager tagsManager;
    private PlayerManager playerManager;
    private Location spawnLocation;
    private String prefix = "§f[§bFlowerCore§f]§r ";

    @Override
    public void onEnable() {
        instance = this;

        long start = System.currentTimeMillis();

        checkDescription();
        registerManagers();
        registerHandlers();
        registerListeners();
        registerCommands();
        loadSpawnLocation();

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        CC.connect();
        CC.on(timeTaken);
    }

    @Override
    public void onDisable() {
        ranksManager.saveToFile();
        mongoManager.saveAllPlayerData();
        mongoManager.close();
        CC.off();
    }

    private void checkDescription() {
        if (!getDescription().getAuthors().contains("Emmy") || !getDescription().getName().contains("FlowerCore")) {
            System.exit(0);
        } else {
            Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Loading..."));
        }
    }

    private void registerManagers() {
        long start = System.currentTimeMillis();
        configHandler = new ConfigHandler();

        framework = new CommandFramework();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Successfully registered PlaceholderAPI expansion."));
            new Placeholder().register();
        }

        this.tagsManager = new TagsManager();
        this.tagsManager.loadConfig();

        this.ranksManager = new RanksManager();
        this.ranksManager.loadConfig();

        this.mongoManager = new MongoManager();
        this.mongoManager.initializeMongo();

        this.playerManager = new PlayerManager();

        new AnnouncementManager();

        long end = System.currentTimeMillis();
        long timeTaken = end - start;
        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all managers in " + timeTaken + "ms."));
    }

    private void registerHandlers() {
        long start = System.currentTimeMillis();
        conversationHandler = new ConversationHandler();

        long end = System.currentTimeMillis();
        long timeTaken = end - start;
        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all handlers in " + timeTaken + "ms."));
    }

    private void registerListeners() {
        long start = System.currentTimeMillis();

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new CommandListener(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all listeners in " + timeTaken + "ms."));
    }

    private void registerCommands() {
        long start = System.currentTimeMillis();

        new FlowerCoreCommand();
        new FlyCommand();
        new gmcCommand();
        new gmsCommand();
        new gmaCommand();
        new InfoCommand();
        new gmspCommand();
        new HealCommand();
        new JoinCommand();
        new FeedCommand();
        new PingCommand();
        new MoreCommand();
        new TrapCommand();
        new SudoCommand();
        new NewsCommand();
        new TrollCommand();
        new StoreCommand();
        new SpeedCommand();
        new ReplyCommand();
        new CraftCommand();
        new ClearCommand();
        new TikTokCommand();
        new LaunchCommand();
        new RocketCommand();
        new RenameCommand();
        new RebootCommand();
        new CaptureCommand();
        new YouTubeCommand();
        new DiscordCommand();
        new MessageCommand();
        new TwitterCommand();
        new WebsiteCommand();
        new SettingsCommand();
        new ForceFlyCommand();
        new AnnounceCommand();
        new InstanceCommand();
        new TeleportCommand();
        new SetJoinLocation();
        new LocationCommand();
        new BroadcastCommand();
        new ClearChatCommand();
        new TeamSpeakCommand();
        new TeleportUpCommand();
        new TrollSilentCommand();
        new TeleportAllCommand();
        new TeleportHereCommand();
        new TeleportSpawnCommand();
        new TrollEverybodyCommand();
        new TeleportPositionCommand();

        new TagCommand();
        new TagListCommand();
        new TagSaveCommand();
        new TagAdminCommand();
        new TagCreateCommand();
        new TagDeleteCommand();
        new TagSetDisplayCommand();

        new RankCommand();
        new GrantCommand();
        new RankListCommand();
        new RankSaveCommand();
        new RankCreateCommand();
        new RankDeleteCommand();
        new RankSetIconCommand();
        new RankSetStaffCommand();
        new RankSetColorCommand();
        new RankSetSuffixCommand();
        new RankSetPrefixCommand();
        new RankSetDisplayCommand();
        new RankSetDefaultCommand();
        new RankSetPriorityCommand();
        new RankAddPermissionsCommand();

        new ToggleGlobalChatCommand();
        new TogglePrivateMessageSounds();
        new TogglePrivateMessagesCommand();

        new BanCommand();
        new AltsCommand();
        new KickCommand();
        new MuteCommand();
        new BlacklistCommand();

        new UnbanCommand();
        new UnMuteCommand();
        new UnblacklistCommand();

        new DayCommand();
        new NightCommand();
        new SunsetCommand();

        new AlertCommand();
        new GodModeCommand(this);

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all commands in " + timeTaken + "ms."));
    }

    private void loadSpawnLocation() {
        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "&fSpawn location has been loaded."));
        FileConfiguration config = configHandler.getConfigByName("settings.yml");
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