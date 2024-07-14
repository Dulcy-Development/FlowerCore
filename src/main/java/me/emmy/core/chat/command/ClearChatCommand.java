package me.emmy.core.chat.command;

import me.emmy.core.FlowerCore;
import me.emmy.core.locale.Locale;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 30/03/2024 - 16:56
 */
public class ClearChatCommand extends BaseCommand {

    private final FlowerCore plugin = FlowerCore.getInstance();

    @Command(name = "clearchat", aliases = {"chatclear"}, permission = "flower.command.clearchat", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        for (int i = 0; i < 1000; i++) {
            Bukkit.broadcastMessage("");
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("flowercore.command.staff")) {
                String staffMessage = CC.translate(plugin.getConfig("messages.yml").getString("clear-chat-staff")
                        .replace("%player%", sender.getName())
                        .replace("%server%", Locale.SERVER_NAME));
                onlinePlayer.sendMessage(staffMessage);

                String regularMessage = CC.translate(plugin.getConfig("messages.yml").getString("clear-chat")
                        .replace("%player%", sender.getName()));
                onlinePlayer.sendMessage(regularMessage);
            } else {
                String regularMessage = CC.translate(plugin.getConfig("messages.yml").getString("clear-chat")
                        .replace("%player%", sender.getName()));
                onlinePlayer.sendMessage(regularMessage);
            }
        }

        String consoleMessage = CC.translate(plugin.getConfig("messages.yml").getString("clear-chat-staff")
                .replace("%player%", sender.getName())
                .replace("%server%", Locale.SERVER_NAME));
        Bukkit.getConsoleSender().sendMessage(consoleMessage);
    }
}