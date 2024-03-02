package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class BroadcastCommand extends BaseCommand {

    private final FlowerCore plugin;

    public BroadcastCommand(FlowerCore plugin) {
        this.plugin = plugin;
    }

    @Command(name = "broadcast", inGameOnly = false, permission = "flower.command.broadcast")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        List<String> text = Arrays.asList(args.getArgs());
        List<String> broadcastMessages = FlowerCore.getInstance().getConfig("messages.yml").getStringList("broadcast.message");

        String barsFormat = CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("broadcast.bars-format"));

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