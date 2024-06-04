package me.emmiesa.flowercore.command.global.server;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.BungeeUtil;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.Completer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 24/03/2024 - 12:16
 */

public class JoinCommand extends BaseCommand {

    @Completer(name = "join", aliases = "joinserver")
    public List<String> joinCompleter(CommandArgs args) {
        List<String> commands = new ArrayList<>();
        if (args.getPlayer().hasPermission("flowercore.staff")) {
            commands.addAll(FlowerCore.getInstance().getConfig("settings.yml").getStringList("bungee-servers"));
        }

        return commands;
    }

    //TODO: The completer needs to display the available bungeecord servers.

    @Override
    @Command(name = "join", aliases = {"joinserver"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.length() == 0) {
            player.sendMessage(CC.translate("&cUsage: /join (server)"));
            return;
        }

        String server = command.getArgs(0);
        BungeeUtil.sendPlayer(player, server);
    }
}