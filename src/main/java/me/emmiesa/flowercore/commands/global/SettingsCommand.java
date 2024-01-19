package me.emmiesa.flowercore.commands.global;

import me.emmiesa.flowercore.menus.settings.SettingsMenu;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class SettingsCommand extends BaseCommand {

    @Command(name = "settings", aliases = {"playeroptions"})
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        new SettingsMenu().openMenu(player);
    }
}