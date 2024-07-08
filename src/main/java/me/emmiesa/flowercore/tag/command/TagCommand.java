package me.emmiesa.flowercore.tag.command;

import me.emmiesa.flowercore.tag.menu.TagsMenu;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
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