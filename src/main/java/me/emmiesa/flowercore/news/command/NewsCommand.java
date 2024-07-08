package me.emmiesa.flowercore.news.command;

import me.emmiesa.flowercore.news.menu.NewsMenu;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 20/03/2024 - 20:08
 */
public class NewsCommand extends BaseCommand {
    @Override
    @Command(name = "news", aliases = {"changelog"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        new NewsMenu().openMenu(player);
    }
}