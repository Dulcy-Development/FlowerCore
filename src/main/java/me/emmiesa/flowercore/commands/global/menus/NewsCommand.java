package me.emmiesa.flowercore.commands.global.menus;

import me.emmiesa.flowercore.menus.news.NewsMenu;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 20/03/2024 - 20:08
 * GitHub: https://github.com/Emmiesa
 */

public class NewsCommand extends BaseCommand {
    @Override
    @Command(name = "news", aliases = {"changelog"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        new NewsMenu().openMenu(player);
    }
}