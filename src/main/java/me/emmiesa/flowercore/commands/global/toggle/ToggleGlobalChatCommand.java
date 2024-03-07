package me.emmiesa.flowercore.commands.global.toggle;

import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class ToggleGlobalChatCommand extends BaseCommand {
    @Override
    @Command(name = "tgc", aliases = "toggleglobalchat")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        // I'm too lazy :(
    }
}
