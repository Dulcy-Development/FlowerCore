package me.emmy.core.command.admin.management;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class AlertCommand extends BaseCommand {
    @Override
    @Command(name = "alert", inGameOnly = false, permission = "flower.command.alert", aliases = "serveralert")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        List<String> text = Arrays.asList(args.getArgs());
        List<String> broadcastMessages = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getStringList("alert.message");

        String barsFormat = CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("alert.bars-format"));

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