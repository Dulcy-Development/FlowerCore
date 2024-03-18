package me.emmiesa.flowercore.commands.global.settings;

import me.emmiesa.flowercore.menus.settings.SettingsMenu;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class SettingsCommand extends BaseCommand {

    @Command(name = "settings", aliases = {"playeroptions"})
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        new SettingsMenu().openMenu(player);
    }
}