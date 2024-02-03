package me.emmiesa.flowercore;

import org.bukkit.configuration.file.FileConfiguration;

public class Locale {
    /*private static final FileConfiguration commandsConfig = FlowerCore.getInstance().getConfigHandler().getCommandsConfig();
    private static final FileConfiguration defaultConfig = FlowerCore.getInstance().getConfigHandler().getDefaultConfig();
    private static final FileConfiguration databaseConfig = FlowerCore.getInstance().getConfigHandler().getDatabaseConfig();
    private static final FileConfiguration extrasConfig = FlowerCore.getInstance().getConfigHandler().getExtrasConfig();
    private static final FileConfiguration permissionsConfig = FlowerCore.getInstance().getConfigHandler().getPermissionsConfig();
    private static final FileConfiguration ranksConfig = FlowerCore.getInstance().getConfigHandler().getRanksConfig();*/
    private static final FileConfiguration messagesConfig = FlowerCore.getInstance().getConfigHandler().getMessagesConfig();
    private static final FileConfiguration settingsConfig = FlowerCore.getInstance().getConfigHandler().getSettingsConfig();

    public static String DEBUG = "&7(Debug) This is not being handled yet.";
    public static String NO_PERM = messagesConfig.getString("no-perms");

    public static String RANK_NOT_FOUND = messagesConfig.getString("rank.does-not-exist");
    public static String RANK_NOT_SET = "&c(Error) Your rank was not set.";
    public static String RANK_SAVED = messagesConfig.getString("rank.saved");
    public static String RANK_SAVING = messagesConfig.getString("rank.saving");

    public static String SERVER_NAME = settingsConfig.getString("server-name");
    public static String SERVER_REGION = settingsConfig.getString("server-region");

    public static String DISCORD = settingsConfig.getString("socials.discord");
    public static String STORE = settingsConfig.getString("socials.store");
    public static String WEBSITE = settingsConfig.getString("socials.website");
    public static String TEAMSPEAK = settingsConfig.getString("socials.teamspeak");
    public static String TIKTOK = settingsConfig.getString("socials.tiktok");
    public static String TWITTER = settingsConfig.getString("socials.twitter");
    public static String YOUTUBE = settingsConfig.getString("socials.youtube");
}