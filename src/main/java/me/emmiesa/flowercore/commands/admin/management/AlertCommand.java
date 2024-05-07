package me.emmiesa.flowercore.commands.admin.management;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class AlertCommand extends BaseCommand {

    @Command(name = "alert", inGameOnly = false, permission = "flower.command.alert", aliases = "serveralert")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        List<String> text = Arrays.asList(args.getArgs());
        List<String> broadcastMessages = FlowerCore.getInstance().getConfig("messages.yml").getStringList("alert.message");

        String barsFormat = CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("alert.bars-format"));

        if (!text.isEmpty()) {
            for (String message : broadcastMessages) {
                String formattedMessage = message
                        .replace("%text%", String.join(" ", text))
                        .replace("%bars%", barsFormat);
                FlowerCore.getInstance().getServer().broadcastMessage(CC.translate(formattedMessage));
            }
        } else {
            sender.sendMessage(CC.translate("&cUsage: /alert (text)"));
        }
    }
}