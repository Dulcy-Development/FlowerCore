package me.emmy.core.api.command;

import me.emmy.core.FlowerCore;

public abstract class BaseCommand {

    public FlowerCore main = FlowerCore.getInstance();

    public BaseCommand() {
        main.getCommandFramework().registerCommands(this);
    }

    public abstract void onCommand(CommandArgs command);

}
