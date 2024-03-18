package me.emmiesa.flowercore.commands.global.socials;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class DiscordCommand extends BaseCommand {

    @Command(name = "discord", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
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
        //            sender.sendMessage(CC.translate(FlowerCore.getInstance().getConfig().getString("socials.discord.disabled_message").replace("%link%", FlowerCore.getInstance().getConfig("messages.yml").getString("socials.discord.placeholder-format"))));
        //        } else {
        //            sender.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("socials.discord.enabled_message").replace("%discord%", Locale.DISCORD)));
        //        }
        //    }
    }
}