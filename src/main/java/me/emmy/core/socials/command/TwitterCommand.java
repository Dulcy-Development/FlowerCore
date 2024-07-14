package me.emmy.core.socials.command;

import me.emmy.core.FlowerCore;
import me.emmy.core.locale.Locale;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TwitterCommand extends BaseCommand {
    @Override
    @Command(name = "twitter", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String commandName = "twitter";

        boolean enableCommand = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getBoolean("socials." + commandName + ".enabled", true);
        String disabledMessageTemplate = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("socials." + commandName + ".disabled_message");

        if (!enableCommand) {
            String disabledMessage = disabledMessageTemplate.replace("%link%", commandName);

            sender.sendMessage(CC.translate(disabledMessage));
        } else {
            String link = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("socials." + commandName + ".enabled_message").replace("%link%", Locale.TWITTER);
            sender.sendMessage(CC.translate(link));
        }
    }
}
