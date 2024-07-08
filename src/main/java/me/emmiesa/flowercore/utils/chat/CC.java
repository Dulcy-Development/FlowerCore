package me.emmiesa.flowercore.utils.chat;

import lombok.experimental.UtilityClass;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Emmy
 * @project FlowerCore
 * @date 08/07/2024 - 23:10
 */
@UtilityClass
public class CC {

    public String MENU_BAR = ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------";
    public String FLOWER_BAR = translate("&b&lೋღ&b&l&m«-------&f&l&m-------&b&l&m-------»&r&b&lღೋ");
    public String FLOWER_BAR_LONG = translate("&b&lೋღ&b&l&m«-------&f&l&m-----------------&b&l&m-------»&r&b&lღೋ");
    public String FLOWER_BAR_LONG_RED = translate("&4&lೋღ&4&l&m«-------&f&l&m-----------------&4&l&m-------»&r&4&lღೋ");

    /**
     * Send an error message to the console.
     *
     * @param message The message to send.
     */
    public void sendError(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(CC.translate("[CC Util sendError] &c" + message + "!"));  //In Player Manager class!
    }

    /**
     * Translate a string to a colored string.
     *
     * @param message The string to translate.
     * @return The translated string.
     */
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Translate a list of strings to a colored list of strings.
     *
     * @param message The list of strings to translate.
     * @return The translated list of strings.
     */
    public List<String> translate(List<String> message) {
        List<String> list = new ArrayList<>();

        for (String line : message) {
            list.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        return list;
    }

    /**
     * Translate an array of strings to a colored list of strings.
     *
     * @param message The array of strings to translate.
     * @return The translated list of strings.
     */
    public List<String> translate(String[] message) {
        List<String> list = new ArrayList<>();

        for (String line : message) {
            if (line != null) {
                list.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        }

        return list;
    }

    /**
     * Broadcast a message to all players.
     *
     * @param message The message to send.
     */
    public void broadcast(String message) {
        Bukkit.broadcastMessage(translate(message));
    }

    public void listRanks(CommandSender sender) {
        sender.sendMessage(" ");
        sender.sendMessage(CC.translate("&fAll registered FlowerCore rank:"));
        FileConfiguration ranksConfig = FlowerCore.getInstance().getConfig("ranks.yml");
        for (String rankName : ranksConfig.getConfigurationSection("ranks").getKeys(false)) {
            String displayName = ranksConfig.getString("ranks." + rankName + ".displayName", rankName);
            sender.sendMessage(CC.translate(" &f- &b" + displayName));
        }
    }

    public void listTags(CommandSender sender) {
        sender.sendMessage(" ");
        sender.sendMessage(CC.translate("&fAll registered FlowerCore tags:"));
        FileConfiguration tagsConfig = FlowerCore.getInstance().getConfig("tags.yml");
        for (String tagName : tagsConfig.getConfigurationSection("tags").getKeys(false)) {
            String displayName = tagsConfig.getString("tags." + tagName + ".displayName", tagName);
            sender.sendMessage(CC.translate(" &f- &b" + displayName));
        }
    }

    public void listRanks() {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(CC.translate("&fAll registered FlowerCore rank:"));
        FileConfiguration ranksConfig = FlowerCore.getInstance().getConfig("ranks.yml");
        for (String rankName : ranksConfig.getConfigurationSection("ranks").getKeys(false)) {
            String displayName = ranksConfig.getString("ranks." + rankName + ".displayName", rankName);
            Bukkit.getConsoleSender().sendMessage(CC.translate(" &f- &b" + displayName));
        }
    }

    public void listTags() {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(CC.translate("&fAll registered FlowerCore tags:"));
        FileConfiguration tagsConfig = FlowerCore.getInstance().getConfig("tags.yml");
        for (String tagName : tagsConfig.getConfigurationSection("tags").getKeys(false)) {
            String displayName = tagsConfig.getString("tags." + tagName + ".displayName", tagName);
            Bukkit.getConsoleSender().sendMessage(CC.translate(" &f- &b" + displayName));
        }
    }

    public void connect() {
        Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getPrefix() + "&fConnecting to Mongo Database..."));
        Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getPrefix() + " &fMongo Database"));
        Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getPrefix() + "  &f> Host: &f" + FlowerCore.getInstance().getConfig("database.yml").getString("database.uri")));
        Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getPrefix() + "  &f> Database: &f" + FlowerCore.getInstance().getConfig("database.yml").getString("database.database-name")));
        Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getPrefix() + "&fSuccessfully connected to Mongo Database!"));
    }

    public void sendCustomFont() {
        Bukkit.getConsoleSender().sendMessage(CC.translate(""));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&b  ______ _                        "));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&b |  ____| |                       "));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&b | |__  | | _____      _____ _ __ "));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&b |  __| | |/ _ \\ \\ /\\ / / _ \\ '__|"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&b | |    | | (_) \\ V  V /  __/ |   "));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&b |_|    |_|\\___/ \\_/\\_/ \\___|_|   "));
        Bukkit.getConsoleSender().sendMessage(CC.translate(""));
    }

    public void on(long timeTaken) {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(CC.translate("&8&m-----------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" &f| Plugin: &b" + FlowerCore.getInstance().getDescription().getName()));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" &f| Authors: &b" + FlowerCore.getInstance().getDescription().getAuthors().toString().replace("[", "").replace("]", "")));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" "));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" &f| Version: &b" + FlowerCore.getInstance().getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" &f| Link: &b" + FlowerCore.getInstance().getDescription().getWebsite()));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" "));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" &7| Bukkit Server Name: &b" + Bukkit.getServerName()));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" &f| Server Name: &b" + Locale.SERVER_NAME));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" &f| Spigot: &b" + Bukkit.getName()));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" "));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" &f| Ranks: &b" + FlowerCore.getInstance().getConfig("ranks.yml").getConfigurationSection("ranks").getKeys(false).size()));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" &f| Tags: &b" + FlowerCore.getInstance().getConfig("tags.yml").getConfigurationSection("tags").getKeys(false).size()));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" "));
        Bukkit.getConsoleSender().sendMessage(CC.translate(" &f| Load time: &b" + (timeTaken) + " &bms"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&8&m-----------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(" ");
    }

    public void off() {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(CC.translate("&8[&bFlowerCore&8] &fDisabled &bFlowerCore&f!"));
        Bukkit.getConsoleSender().sendMessage(" ");
    }
}