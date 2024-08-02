package me.emmy.core;

import lombok.Getter;
import lombok.Setter;
import me.emmy.core.api.command.CommandFramework;
import me.emmy.core.api.menu.listener.MenuListener;
import me.emmy.core.chat.ChatRepository;
import me.emmy.core.chat.command.ClearChatCommand;
import me.emmy.core.chat.command.MuteChatCommand;
import me.emmy.core.chat.command.UnMuteChatCommand;
import me.emmy.core.chat.listener.ChatListener;
import me.emmy.core.command.FlowerCoreCommand;
import me.emmy.core.command.admin.essential.*;
import me.emmy.core.command.admin.gamemode.gmaCommand;
import me.emmy.core.command.admin.gamemode.gmcCommand;
import me.emmy.core.command.admin.gamemode.gmsCommand;
import me.emmy.core.command.admin.gamemode.gmspCommand;
import me.emmy.core.command.admin.management.*;
import me.emmy.core.command.admin.server.InstanceCommand;
import me.emmy.core.command.admin.teleport.*;
import me.emmy.core.command.admin.troll.*;
import me.emmy.core.command.donator.AnnounceCommand;
import me.emmy.core.command.global.server.JoinCommand;
import me.emmy.core.command.global.server.PingCommand;
import me.emmy.core.command.global.worldtime.DayCommand;
import me.emmy.core.command.global.worldtime.NightCommand;
import me.emmy.core.command.global.worldtime.SunsetCommand;
import me.emmy.core.config.ConfigHandler;
import me.emmy.core.conversation.ConversationHandler;
import me.emmy.core.conversation.command.MessageCommand;
import me.emmy.core.conversation.command.ReplyCommand;
import me.emmy.core.database.MongoService;
import me.emmy.core.essential.godmode.GodModeMemory;
import me.emmy.core.essential.godmode.command.GodModeCommand;
import me.emmy.core.essential.godmode.listener.GodModeListener;
import me.emmy.core.grant.GrantHandler;
import me.emmy.core.grant.command.GrantCommand;
import me.emmy.core.grant.command.GrantsCommand;
import me.emmy.core.listener.CommandListener;
import me.emmy.core.news.command.NewsCommand;
import me.emmy.core.papi.PlaceholderAPI;
import me.emmy.core.profile.ProfileRepository;
import me.emmy.core.profile.listener.ProfileListener;
import me.emmy.core.punishment.command.PunishHistoryCommand;
import me.emmy.core.punishment.command.pardon.UnMuteCommand;
import me.emmy.core.punishment.command.pardon.UnbanCommand;
import me.emmy.core.punishment.command.pardon.UnblacklistCommand;
import me.emmy.core.punishment.command.punish.BanCommand;
import me.emmy.core.punishment.command.punish.BlacklistCommand;
import me.emmy.core.punishment.command.punish.KickCommand;
import me.emmy.core.punishment.command.punish.MuteCommand;
import me.emmy.core.rank.RankRepository;
import me.emmy.core.rank.command.RankCommand;
import me.emmy.core.rank.command.SetRankCommand;
import me.emmy.core.rank.command.SetRankPurchasedCommand;
import me.emmy.core.rank.command.impl.*;
import me.emmy.core.setting.command.SettingsCommand;
import me.emmy.core.setting.command.toggle.ToggleGlobalChatCommand;
import me.emmy.core.setting.command.toggle.TogglePrivateMessageSounds;
import me.emmy.core.setting.command.toggle.TogglePrivateMessagesCommand;
import me.emmy.core.socials.command.*;
import me.emmy.core.spawn.SpawnHandler;
import me.emmy.core.spawn.command.SetJoinLocation;
import me.emmy.core.spawn.command.TeleportSpawnCommand;
import me.emmy.core.tag.TagRepository;
import me.emmy.core.tag.command.TagAdminCommand;
import me.emmy.core.tag.command.TagCommand;
import me.emmy.core.tag.command.impl.*;
import me.emmy.core.tip.TipsHandler;
import me.emmy.core.utils.Cooldown;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.utils.chat.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Dulcy Development @ 2023 - 2024
 *
 * @author Emmy
 * @project FlowerCore
 * @date 24/12/2023 - 10:13
 * @credit <a href="https://github.com/Devuxious">Remi</a>, <a href="https://github.com/Devlrxxh">lrxh</a>, <a href="https://github.com/FrozedClubDevelopment">FCD</a>
 * @link <a href="https://github.com/Dulcy-Development">Dulcy Development</a>
 */
@Getter
@Setter
public class FlowerCore extends JavaPlugin {

    @Getter
    private static FlowerCore instance;

    private ConversationHandler conversationHandler;
    private ProfileRepository profileRepository;
    private CommandFramework commandFramework;
    private ChatRepository chatRepository;
    private RankRepository rankRepository;
    private GodModeMemory godModeMemory;
    private TagRepository tagRepository;
    private ConfigHandler configHandler;
    private GrantHandler grantHandler;
    private SpawnHandler spawnHandler;
    private MongoService mongoService;
    private TipsHandler tipsHandler;
    private Cooldown cooldown;

    private String prefix = "§f[§bFlowerCore§f]§r ";

    @Override
    public void onEnable() {
        instance = this;

        long start = System.currentTimeMillis();

        checkDescription();
        registerManagers();
        registerCommands();
        registerListeners();
        registerChannels();
        registerPapiExpansion();

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

        this.configHandler = new ConfigHandler();

        this.commandFramework = new CommandFramework();

        this.tagRepository = new TagRepository();
        this.tagRepository.loadConfig();

        this.rankRepository = new RankRepository();
        this.rankRepository.loadRanks();

        this.mongoService = new MongoService();
        this.mongoService.initializeMongo();

        this.profileRepository = new ProfileRepository();
        this.profileRepository.loadAllProfiles();

        this.grantHandler = new GrantHandler();

        this.chatRepository = new ChatRepository(false);

        this.spawnHandler = new SpawnHandler();
        this.spawnHandler.loadSpawnLocation();

        this.tipsHandler = new TipsHandler();

        this.conversationHandler = new ConversationHandler();

        this.godModeMemory = new GodModeMemory();

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all managers in " + timeTaken + "ms."));
    }

    private void registerListeners() {
        long start = System.currentTimeMillis();

        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProfileListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new GodModeListener(), this);

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all listeners in " + timeTaken + "ms."));
    }

    private void registerCommands() {
        long start = System.currentTimeMillis();

        //commandFramework.registerCommandsInPackage("me.emmy.core");  no idea why this doesnt fucking work
        new FlowerCoreCommand();
        new FlyCommand();
        new gmcCommand();
        new gmsCommand();
        new gmaCommand();
        new InfoCommand();
        new gmspCommand();
        new HealCommand();
        new JoinCommand();
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
        new GodModeCommand();

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all commands in " + timeTaken + "ms."));
    }

    private void registerChannels() {
        Logger.logTime("Bungee Channel", () ->
                Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord")
        );
    }

    private void registerPapiExpansion() {
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