package me.emmiesa.flowercore;

import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.chat.ChatRepository;
import me.emmiesa.flowercore.chat.command.ClearChatCommand;
import me.emmiesa.flowercore.chat.command.MuteChatCommand;
import me.emmiesa.flowercore.chat.command.UnMuteChatCommand;
import me.emmiesa.flowercore.command.FlowerCoreCommand;
import me.emmiesa.flowercore.command.admin.essential.*;
import me.emmiesa.flowercore.command.admin.gamemode.gmaCommand;
import me.emmiesa.flowercore.command.admin.gamemode.gmcCommand;
import me.emmiesa.flowercore.command.admin.gamemode.gmsCommand;
import me.emmiesa.flowercore.command.admin.gamemode.gmspCommand;
import me.emmiesa.flowercore.command.admin.management.*;
import me.emmiesa.flowercore.command.admin.server.InstanceCommand;
import me.emmiesa.flowercore.command.admin.teleport.*;
import me.emmiesa.flowercore.command.admin.troll.*;
import me.emmiesa.flowercore.command.donator.AnnounceCommand;
import me.emmiesa.flowercore.command.global.server.JoinCommand;
import me.emmiesa.flowercore.command.global.server.PingCommand;
import me.emmiesa.flowercore.command.global.worldtime.DayCommand;
import me.emmiesa.flowercore.command.global.worldtime.NightCommand;
import me.emmiesa.flowercore.command.global.worldtime.SunsetCommand;
import me.emmiesa.flowercore.config.ConfigHandler;
import me.emmiesa.flowercore.conversation.ConversationHandler;
import me.emmiesa.flowercore.conversation.command.MessageCommand;
import me.emmiesa.flowercore.conversation.command.ReplyCommand;
import me.emmiesa.flowercore.database.MongoService;
import me.emmiesa.flowercore.grant.GrantHandler;
import me.emmiesa.flowercore.grant.command.GrantCommand;
import me.emmiesa.flowercore.grant.command.GrantsCommand;
import me.emmiesa.flowercore.chat.listener.ChatListener;
import me.emmiesa.flowercore.listener.CommandListener;
import me.emmiesa.flowercore.news.command.NewsCommand;
import me.emmiesa.flowercore.papi.PlaceholderAPI;
import me.emmiesa.flowercore.profile.ProfileRepository;
import me.emmiesa.flowercore.profile.listener.ProfileListener;
import me.emmiesa.flowercore.punishment.command.PunishHistoryCommand;
import me.emmiesa.flowercore.punishment.command.punish.BanCommand;
import me.emmiesa.flowercore.punishment.command.punish.BlacklistCommand;
import me.emmiesa.flowercore.punishment.command.punish.KickCommand;
import me.emmiesa.flowercore.punishment.command.punish.MuteCommand;
import me.emmiesa.flowercore.punishment.command.pardon.UnMuteCommand;
import me.emmiesa.flowercore.punishment.command.pardon.UnbanCommand;
import me.emmiesa.flowercore.punishment.command.pardon.UnblacklistCommand;
import me.emmiesa.flowercore.rank.RankRepository;
import me.emmiesa.flowercore.rank.command.RankCommand;
import me.emmiesa.flowercore.rank.command.SetRankCommand;
import me.emmiesa.flowercore.rank.command.SetRankPurchasedCommand;
import me.emmiesa.flowercore.rank.command.impl.*;
import me.emmiesa.flowercore.settings.command.SettingsCommand;
import me.emmiesa.flowercore.settings.command.toggle.ToggleGlobalChatCommand;
import me.emmiesa.flowercore.settings.command.toggle.TogglePrivateMessageSounds;
import me.emmiesa.flowercore.settings.command.toggle.TogglePrivateMessagesCommand;
import me.emmiesa.flowercore.socials.command.*;
import me.emmiesa.flowercore.spawn.SpawnHandler;
import me.emmiesa.flowercore.spawn.command.SetJoinLocation;
import me.emmiesa.flowercore.spawn.command.TeleportSpawnCommand;
import me.emmiesa.flowercore.tag.TagRepository;
import me.emmiesa.flowercore.tag.command.TagAdminCommand;
import me.emmiesa.flowercore.tag.command.TagCommand;
import me.emmiesa.flowercore.tag.command.impl.*;
import me.emmiesa.flowercore.tips.TipsHandler;
import me.emmiesa.flowercore.utils.Cooldown;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.CommandFramework;
import me.emmiesa.flowercore.api.menu.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
@Setter
public class FlowerCore extends JavaPlugin {

    @Getter
    private static FlowerCore instance;

    private ConversationHandler conversationHandler;
    private ProfileRepository profileRepository;
    private CommandFramework commandFramework;
    private RankRepository rankRepository;
    private ChatRepository chatRepository;
    private TagRepository tagRepository;
    private ConfigHandler configHandler;
    private MongoService mongoService;
    private GrantHandler grantHandler;
    private SpawnHandler spawnHandler;
    private TipsHandler tipsHandler;
    private Location spawnLocation;
    private Cooldown cooldown;

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
        registerPlaceholders();

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        CC.connect();
        CC.on(timeTaken);
    }

    @Override
    public void onDisable() {
        rankRepository.saveToFile();
        mongoService.saveAllPlayerData();
        mongoService.close();
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

        commandFramework = new CommandFramework();

        this.tagRepository = new TagRepository();
        this.tagRepository.loadConfig();

        this.rankRepository = new RankRepository();
        this.rankRepository.loadRanks();

        this.mongoService = new MongoService();
        this.mongoService.initializeMongo();

        this.profileRepository = new ProfileRepository();
        this.profileRepository.loadAllProfiles();

        this.tipsHandler = new TipsHandler();

        this.spawnHandler = new SpawnHandler();
        this.spawnHandler.loadSpawnLocation();

        this.chatRepository = new ChatRepository(false);

        long end = System.currentTimeMillis();
        long timeTaken = end - start;
        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all managers in " + timeTaken + "ms."));
    }

    private void registerHandlers() {
        long start = System.currentTimeMillis();
        conversationHandler = new ConversationHandler();
        grantHandler = new GrantHandler();

        long end = System.currentTimeMillis();
        long timeTaken = end - start;
        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all handlers in " + timeTaken + "ms."));
    }

    private void registerListeners() {
        long start = System.currentTimeMillis();

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new ProfileListener(), this);
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
        new FakeOpCommand();
        new CaptureCommand();
        new YouTubeCommand();
        new DiscordCommand();
        new MessageCommand();
        new TwitterCommand();
        new WebsiteCommand();
        new SettingsCommand();
        new AnnounceCommand();
        new InstanceCommand();
        new TeleportCommand();
        new SetJoinLocation();
        new LocationCommand();
        new BroadcastCommand();
        new TeamSpeakCommand();
        new TeleportUpCommand();
        new TrollSilentCommand();
        new TeleportAllCommand();
        new TeleportHereCommand();
        new TeleportSpawnCommand();
        new PunishHistoryCommand();
        new TrollEverybodyCommand();
        new TeleportPositionCommand();

        new ClearChatCommand();
        new MuteChatCommand();
        new UnMuteChatCommand();

        new TagCommand();
        new TagListCommand();
        new TagSaveCommand();
        new TagAdminCommand();
        new TagCreateCommand();
        new TagSettagCommand();
        new TagDeleteCommand();
        new TagSetDisplayCommand();

        new RankCommand();
        new GrantCommand();
        new GrantsCommand();
        new SetRankCommand();
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
        new SetRankPurchasedCommand();
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

    private void registerPlaceholders() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Successfully registered PlaceholderAPI expansion."));
            new PlaceholderAPI().register();
        }
    }

    public FileConfiguration getConfig(String fileName) {
        File configFile = new File(getDataFolder(), fileName);
        return YamlConfiguration.loadConfiguration(configFile);
    }
}