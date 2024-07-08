package me.emmiesa.flowercore.socials.command;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
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

        boolean enableCommand = FlowerCore.getInstance().getConfig("messages.yml").getBoolean("socials." + commandName + ".enabled", true);
        String disabledMessageTemplate = FlowerCore.getInstance().getConfig().getString("socials." + commandName + ".disabled_message");

        if (!enableCommand) {
            String disabledMessage = disabledMessageTemplate.replace("%link%", commandName);

            sender.sendMessage(CC.translate(disabledMessage));
        } else {
            String link = FlowerCore.getInstance().getConfig("messages.yml").getString("socials." + commandName + ".enabled_message").replace("%link%", Locale.DISCORD);
            sender.sendMessage(CC.translate(link));
        }

        //
        //      The better version. No idea why I do not use it :>
        //
        //@Command(name = "discord", inGameOnly = false)
        //    public void onCommand(CommandArgs args) {
        //        CommandSender sender = args.getSender();
        //
        //        if (!FlowerCore.getInstance().getConfig("messages.yml").getBoolean("socials.discord.enabled")) {
        //            sender.sendMessage(CC.translate(FlowerCore.getInstance().getConfig().getString("socials.discord.disabled_message").replace("%link%", FlowerCore.getInstance().getConfig("messages.yml").getString("socials.discord.papi-format"))));
        //        } else {
        //            sender.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("socials.discord.enabled_message").replace("%discord%", Locale.DISCORD)));
        //        }
        //    }
    }
}