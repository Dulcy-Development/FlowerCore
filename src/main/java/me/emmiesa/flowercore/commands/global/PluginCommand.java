package me.emmiesa.flowercore.commands.global;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Lang;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PluginCommand extends BaseCommand {

    @Command(name = "serverplugins", aliases = {"plugininformation", "listplugins", "pluginslist", "plugin", "pl", "plugins"}, inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        List<String> pluginNames = Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .map(Plugin::getName)
                .sorted()
                .collect(Collectors.toList());

        if (sender.hasPermission(FlowerCore.instance.getConfig("commands.yml").getString("plugin.permission.perm"))) {
            sender.sendMessage(" ");
            sender.sendMessage(CC.translate("&d&lೋღ&d&l&m«-------&f&l&m-------&d&l&m-------»&r&d&lღೋ"));
            sender.sendMessage(CC.translate("&f     All &dPlugins &fon the server:"));
            for (String pluginName : pluginNames) {
                sender.sendMessage(CC.translate("      &f┃ &d" + pluginName));
            }
            sender.sendMessage(CC.translate("&d&lೋღ&d&l&m«-------&f&l&m-------&d&l&m-------»&r&d&lღೋ"));
            sender.sendMessage(" ");
        } else {
            if (FlowerCore.instance.getConfig("commands.yml").getBoolean("plugin.permission.perm-off")) {
                sender.sendMessage(" ");
                sender.sendMessage(CC.translate("&d&lೋღ&d&l&m«-------&f&l&m-------&d&l&m-------»&r&d&lღೋ"));
                sender.sendMessage(CC.translate("&f     All &dPlugins &fon the server:"));
                for (String pluginName : pluginNames) {
                    sender.sendMessage(CC.translate("      &f┃ &d" + pluginName));
                }
                sender.sendMessage(CC.translate("&d&lೋღ&d&l&m«-------&f&l&m-------&d&l&m-------»&r&d&lღೋ"));
                sender.sendMessage(" ");
            } else {
                sender.sendMessage(CC.translate(Lang.NO_PERM));
            }
        }
    }
}