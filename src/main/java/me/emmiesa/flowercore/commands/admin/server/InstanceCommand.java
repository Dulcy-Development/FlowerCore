package me.emmiesa.flowercore.commands.admin.server;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class InstanceCommand extends BaseCommand {

    @Command(name = "instance", aliases = "serverdetails", permission = "flower.command.instance", inGameOnly = false)

    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        for (String message : FlowerCore.getInstance().getConfig("messages.yml").getStringList("instance-command")) {
            sender.sendMessage(CC.translate(message)
                    .replace("%server-region%", Locale.SERVER_REGION)
                    .replace("%server-name%", Locale.SERVER_NAME)
                    .replace("%bukkit-server-name%", Bukkit.getServerName())
                    .replace("%version%", Bukkit.getServer().getVersion())
                    .replace("%spigot%", Bukkit.getServer().getName())
                    .replace("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                    .replace("%online-players%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
            );
        }
    }
}