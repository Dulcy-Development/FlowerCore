package me.emmiesa.flowercore.commands.global;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Lang;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class TeamSpeakCommand extends BaseCommand {

    @Command(name = "teamspeak", aliases = {"ts"}, inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        String commandName = "teamspeak";

        boolean enableCommand = FlowerCore.instance.getConfig("messages.yml").getBoolean("socials." + commandName + ".enabled", true);
        String disabledMessageTemplate = FlowerCore.instance.getConfig().getString("socials." + commandName + ".disabled_message");

        if (!enableCommand) {
            String disabledMessage = disabledMessageTemplate.replace("%link%", commandName);

            sender.sendMessage(CC.translate(disabledMessage));
        } else {
            String link = FlowerCore.instance.getConfig("messages.yml").getString("socials." + commandName + ".enabled_message").replace("%link%", Lang.TEAMSPEAK);
            sender.sendMessage(CC.translate(link));
        }
    }
}

