package me.emmiesa.flowercore.tag.command.impl;

import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class TagSetIconCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.seticon", permission = "flower.tags.developer")
    public void onCommand(CommandArgs command) {
        //CommandSender sender = command.getSender();
        //Player player = command.getPlayer();
    }
}