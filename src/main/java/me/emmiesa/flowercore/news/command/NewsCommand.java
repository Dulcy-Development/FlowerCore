package me.emmiesa.flowercore.news.command;

import me.emmiesa.flowercore.news.menu.NewsMenu;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
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