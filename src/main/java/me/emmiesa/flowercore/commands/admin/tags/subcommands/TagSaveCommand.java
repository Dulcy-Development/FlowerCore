package me.emmiesa.flowercore.commands.admin.tags.subcommands;

import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;

public class TagSaveCommand extends BaseCommand {
    @Override
    @Command(name = "tag.save", permission = "flower.staff")
    public void onCommand(CommandArgs command) {
        //CommandSender sender = command.getSender();
        //Player player = command.getPlayer();
    }
}
