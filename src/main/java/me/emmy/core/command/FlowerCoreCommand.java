package me.emmy.core.command;

import me.emmy.core.FlowerCore;
import me.emmy.core.locale.Locale;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import me.emmy.core.api.command.annotation.Completer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class FlowerCoreCommand extends BaseCommand {
    @Completer(name = "flower", aliases = {"flowercore", "flowercore"})
    public List<String> flowerCompleter(CommandArgs args) {
        List<String> command = new ArrayList<>();
        if (args.length() == 1) {
            if (args.getPlayer().hasPermission("flowercore.admin")) {
                command.add("reload");
            }
        }
        return command;
    }

    @Override
    @Command(name = "flowercore", aliases = {"flower", "flowercore"}, inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() == 0) {
            sender.sendMessage(" ");
            sender.sendMessage(CC.FLOWER_BAR);
            sender.sendMessage(CC.translate("  &b&l   FlowerCore"));
            sender.sendMessage(CC.translate("      &f┃ Author: &b" + FlowerCore.getInstance().getDescription().getAuthors().get(0)));
            sender.sendMessage(CC.translate("      &f┃ Version: &b" + FlowerCore.getInstance().getDescription().getVersion()));
            sender.sendMessage(CC.translate(" "));
            sender.sendMessage(CC.translate("  &b&l   Description:"));
            sender.sendMessage(CC.translate("      &f┃ " + FlowerCore.getInstance().getDescription().getDescription()));
            sender.sendMessage(CC.FLOWER_BAR);
            sender.sendMessage(" ");
        } else if (args.length() >= 1) {
            noPermission(sender, args);
        }
    }

    private void noPermission(CommandSender sender, CommandArgs args) {
        if (args.getArgs(0).equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("flowercore.admin")) {
                sender.sendMessage(CC.translate(Locale.NO_PERM));
                return;
            }

            long start = System.currentTimeMillis();

            for (String message : FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getStringList("reload.reloading")) {
                sender.sendMessage(CC.translate(message));
            }

            FlowerCore.getInstance().getConfigHandler().reloadConfigs();

            long end = System.currentTimeMillis();
            long timeTaken = end - start;

            for (String message : FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getStringList("reload.finished")) {
                sender.sendMessage(CC.translate(message.replace("%timetaken%", String.valueOf(timeTaken))));
            }
        }
    }
}