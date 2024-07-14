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
public class DiscordCommand extends BaseCommand {
    @Override
    @Command(name = "discord", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String commandName = "discord";

        boolean enableCommand = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getBoolean("socials." + commandName + ".enabled", true);
        String disabledMessageTemplate = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("socials." + commandName + ".disabled_message");

        if (!enableCommand) {
            String disabledMessage = disabledMessageTemplate.replace("%link%", commandName);

            sender.sendMessage(CC.translate(disabledMessage));
        } else {
            String link = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("socials." + commandName + ".enabled_message").replace("%link%", Locale.DISCORD);
            sender.sendMessage(CC.translate(link));
        }

        //
        //      The better version. No idea why I do not use it :>
        //
        //@Command(name = "discord", inGameOnly = false)
        //    public void onCommand(CommandArgs args) {
        //        CommandSender sender = args.getSender();
        //
        //        if (!FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getBoolean("socials.discord.enabled")) {
        //            sender.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig().getString("socials.discord.disabled_message").replace("%link%", FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("socials.discord.papi-format"))));
        //        } else {
        //            sender.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("socials.discord.enabled_message").replace("%discord%", Locale.DISCORD)));
        //        }
        //    }
    }
}