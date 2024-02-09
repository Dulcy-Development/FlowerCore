package me.emmiesa.flowercore.commands.admin.administration;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCommand extends BaseCommand {

    private final FlowerCore plugin = FlowerCore.get();

    @Command(name = "clearchat", aliases = {"chatclear"}, permission = "flower.command.staff", inGameOnly = false)
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