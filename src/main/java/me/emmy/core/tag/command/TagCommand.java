package me.emmy.core.tag.command;

import me.emmy.core.tag.menu.TagsMenu;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 30/03/2024 - 18:37
 */
public class TagCommand extends BaseCommand {
    @Override
    @Command(name = "tag")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        new TagsMenu(player.getDisplayName()).openMenu(player);
    }
}