package me.emmiesa.flowercore.settings.command;

import me.emmiesa.flowercore.settings.menu.SettingsMenu;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class SettingsCommand extends BaseCommand {
    @Override
    @Command(name = "settings", aliases = {"playeroptions"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        new SettingsMenu().openMenu(player);
    }
}