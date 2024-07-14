package me.emmy.core.setting.command;

import me.emmy.core.setting.menu.SettingsMenu;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class SettingsCommand extends BaseCommand {
    @Override
    @Command(name = "setting", aliases = {"playeroptions"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        new SettingsMenu().openMenu(player);
    }
}