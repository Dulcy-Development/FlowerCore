package me.emmiesa.flowercore.commands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import me.emmiesa.flowercore.utils.command.Completer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class FlowerCoreCommand extends BaseCommand {

    private final FlowerCore plugin = FlowerCore.getInstance();

    @Completer(name = "flower", aliases = {"flowercore", "core"})
    public List<String> flowerCompleter(CommandArgs args) {
        List<String> add = new ArrayList<>();
        if (args.length() == 1) {
            add.add("reload");
        }
        return add;
    }

    @Command(name = "flowercore", aliases = {"flower", "core"}, inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() == 0) {
            sender.sendMessage(" ");
            sender.sendMessage(CC.FLOWER_BAR);
            sender.sendMessage(CC.translate("  &b&l   FlowerCore"));
            sender.sendMessage(CC.translate("      &f┃ Author: &b" + plugin.getDescription().getAuthors().toString().replace("[", "").replace("]", "")));
            sender.sendMessage(CC.translate("      &f┃ Version: &b" + plugin.getDescription().getVersion()));
            sender.sendMessage(CC.translate(" "));
            sender.sendMessage(CC.translate("  &b&l   Description:"));
            sender.sendMessage(CC.translate("      &f┃ " + plugin.getDescription().getDescription()));
            sender.sendMessage(CC.FLOWER_BAR);
            sender.sendMessage(" ");
        } else if (args.length() >= 1) {
            noperm(sender, args);
        }
    }

    private void noperm(CommandSender sender, CommandArgs args) {
        if (args.getArgs(0).equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("flowercore.admin")) {
                sender.sendMessage(CC.translate(Locale.NO_PERM));
                return;
            }

            long start = System.currentTimeMillis();

            for (String message : plugin.getConfig("messages.yml").getStringList("reload.reloading")) {
                sender.sendMessage(CC.translate(message));
            }

            FlowerCore.getInstance().getConfigHandler().reloadConfigs();

            long end = System.currentTimeMillis();
            long timeTaken = end - start;

            for (String message : plugin.getConfig("messages.yml").getStringList("reload.finished")) {
                sender.sendMessage(CC.translate(message.replace("%timetaken%", String.valueOf(timeTaken))));
            }
        }
    }
}