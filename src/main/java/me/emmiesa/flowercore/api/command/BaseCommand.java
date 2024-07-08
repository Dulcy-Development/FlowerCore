package me.emmiesa.flowercore.api.command;

import me.emmiesa.flowercore.FlowerCore;

public abstract class BaseCommand {

    public FlowerCore main = FlowerCore.getInstance();

    public BaseCommand() {
        main.getCommandFramework().registerCommands(this);
    }

    public abstract void onCommand(CommandArgs command);

}
