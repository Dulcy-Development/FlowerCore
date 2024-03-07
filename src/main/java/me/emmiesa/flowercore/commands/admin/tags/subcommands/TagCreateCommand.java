package me.emmiesa.flowercore.commands.admin.tags.subcommands;

import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCreateCommand extends BaseCommand {
    @Override
    @Command(name = "tag.create", permission = "flower.staff")
    public void onCommand(CommandArgs command) {
        //CommandSender sender = command.getSender();
        //Player player = command.getPlayer();
    }
}
