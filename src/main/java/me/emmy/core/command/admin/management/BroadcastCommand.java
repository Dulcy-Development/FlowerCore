package me.emmy.core.command.admin.management;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.Utils;
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
public class BroadcastCommand extends BaseCommand {
    @Override
    @Command(name = "broadcast", inGameOnly = false, permission = "flower.command.broadcast")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        List<String> text = Arrays.asList(args.getArgs());
        List<String> broadcastMessages = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getStringList("broadcast.message");

        String barsFormat = CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("broadcast.bars-format"));

        if (!text.isEmpty()) {
            for (String message : broadcastMessages) {
                String formattedMessage = message
                        .replace("%text%", String.join(" ", text))
                        .replace("%bars%", barsFormat);
                Utils.broadcastMessage(CC.translate(formattedMessage));
            }
        } else {
            sender.sendMessage(CC.translate("&cUsage: /broadcast (text)"));
        }
    }
}