package me.emmiesa.flowercore.locale;

import me.emmiesa.flowercore.FlowerCore;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class Locale {
    private static final FileConfiguration messagesConfig = FlowerCore.getInstance().getConfigHandler().getConfigByName("messages.yml");
    private static final FileConfiguration settingsConfig = FlowerCore.getInstance().getConfigHandler().getConfigByName("settings.yml");

    public static String DEBUG = "&7(Debug) This is not being handled yet.";
    public static String CMD_CANT_BE_USED = "&4This command cannot be used currently because It's still in development.";
    public static String PLAYER_NOT_ONLINE = "&cNo player matching &4%player% &cis connected to this server.";
    public static String PROFILE_NOT_FOUND = "&cProfile not found!";
    public static String NO_PERM = messagesConfig.getString("no-perms");

    public static String RANK_NOT_FOUND = messagesConfig.getString("rank.does-not-exist");
    public static String RANK_NOT_SET = messagesConfig.getString("rank.not-set-error");
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