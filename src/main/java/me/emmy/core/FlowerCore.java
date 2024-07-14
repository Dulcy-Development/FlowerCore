package me.emmy.core;

import lombok.Getter;
import lombok.Setter;
import me.emmy.core.api.command.CommandFramework;
import me.emmy.core.api.menu.MenuListener;
import me.emmy.core.chat.ChatRepository;
import me.emmy.core.chat.listener.ChatListener;
import me.emmy.core.config.ConfigHandler;
import me.emmy.core.conversation.ConversationHandler;
import me.emmy.core.database.MongoService;
import me.emmy.core.essential.godmode.GodModeMemory;
import me.emmy.core.essential.godmode.listener.GodModeListener;
import me.emmy.core.grant.GrantHandler;
import me.emmy.core.listener.CommandListener;
import me.emmy.core.papi.PlaceholderAPI;
import me.emmy.core.profile.ProfileRepository;
import me.emmy.core.profile.listener.ProfileListener;
import me.emmy.core.rank.RankRepository;
import me.emmy.core.spawn.SpawnHandler;
import me.emmy.core.tag.TagRepository;
import me.emmy.core.tip.TipsHandler;
import me.emmy.core.utils.Cooldown;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.utils.chat.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

@Getter
@Setter
public class FlowerCore extends JavaPlugin {

    @Getter
    private static FlowerCore instance;

    private GodModeMemory godModeMemory;
    private ChatRepository chatRepository;
    private ProfileRepository profileRepository;
    private GrantHandler grantHandler;
    private CommandFramework commandFramework;
    private SpawnHandler spawnHandler;
    private ConversationHandler conversationHandler;
    private TagRepository tagRepository;
    private ConfigHandler configHandler;
    private MongoService mongoService;
    private RankRepository rankRepository;
    private TipsHandler tipsHandler;
    private Cooldown cooldown;
    private Location spawnLocation;

    private String prefix = "§f[§bFlowerCore§f]§r ";

    @Override
    public void onEnable() {
        instance = this;

        long start = System.currentTimeMillis();

        checkDescription();
        registerHandlers();
        registerRepositories();
        registerServices();
        registerListeners();
        registerCommands();
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

    private void registerHandlers() {
        long start = System.currentTimeMillis();

        configHandler = new ConfigHandler();

        this.spawnHandler = new SpawnHandler();
        this.spawnHandler.loadSpawnLocation();

        this.grantHandler = new GrantHandler();

        this.tipsHandler = new TipsHandler();

        this.conversationHandler = new ConversationHandler();

        this.godModeMemory = new GodModeMemory();

        long end = System.currentTimeMillis();
        long timeTaken = end - start;
        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all handlers in " + timeTaken + "ms."));
    }

    private void registerRepositories() {
        long start = System.currentTimeMillis();

        this.profileRepository = new ProfileRepository();

        this.rankRepository = new RankRepository();
        this.rankRepository.loadRanks();

        this.tagRepository = new TagRepository();
        this.tagRepository.loadConfig();

        this.chatRepository = new ChatRepository(false);

        long end = System.currentTimeMillis();
        long timeTaken = end - start;
        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all repositories in " + timeTaken + "ms."));
    }

    private void registerServices() {
        long start = System.currentTimeMillis();

        this.mongoService = new MongoService();
        this.mongoService.initializeMongo();
        this.mongoService.loadAllProfiles();

        long end = System.currentTimeMillis();
        long timeTaken = end - start;
        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all services in " + timeTaken + "ms."));
    }

    private void registerListeners() {
        long start = System.currentTimeMillis();

        Arrays.asList(
                new ProfileListener(),
                new ChatListener(),
                new CommandListener(),
                new MenuListener(),
                new GodModeListener()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered all listeners in " + timeTaken + "ms."));
    }

    private void registerCommands() {
        long start = System.currentTimeMillis();

        commandFramework = new CommandFramework();
        commandFramework.registerCommandsInPackage("me.emmy.core").forEach(command ->
                Bukkit.getConsoleSender().sendMessage(CC.translate(prefix + "Registered command: " + command.getName()))
        );

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