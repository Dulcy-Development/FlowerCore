package me.emmy.core.news.command;

import me.emmy.core.news.menu.NewsMenu;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
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