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
public class TeamSpeakCommand extends BaseCommand {
    @Override
    @Command(name = "teamspeak", aliases = {"ts"}, inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        String commandName = "teamspeak";

        boolean enableCommand = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getBoolean("socials." + commandName + ".enabled", true);
        String disabledMessageTemplate = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("socials." + commandName + ".disabled_message");

        if (!enableCommand) {
            String disabledMessage = disabledMessageTemplate.replace("%link%", commandName);

            sender.sendMessage(CC.translate(disabledMessage));
        } else {
            String link = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("socials." + commandName + ".enabled_message").replace("%link%", Locale.TEAMSPEAK);
            sender.sendMessage(CC.translate(link));
        }
    }
}

