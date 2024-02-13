package me.emmiesa.flowercore.punishments.command;

import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;


public class BanCommand extends BaseCommand {

    @Command(name = "ban", inGameOnly = false, permission = "flower.punishment.ban")
    public void onCommand(CommandArgs args) {
    }
}
