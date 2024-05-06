package me.emmiesa.flowercore.utils.command;

import me.emmiesa.flowercore.FlowerCore;

public abstract class BaseCommand {

    public FlowerCore main = FlowerCore.getINSTANCE();

    public BaseCommand() {
        main.getFramework().registerCommands(this);
    }

    public abstract void onCommand(CommandArgs command);

}
