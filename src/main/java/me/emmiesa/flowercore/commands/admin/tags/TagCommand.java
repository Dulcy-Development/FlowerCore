package me.emmiesa.flowercore.commands.admin.tags;

import me.emmiesa.flowercore.menus.tags.TagsMenu;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 30/03/2024 - 18:37
 */

public class TagCommand extends BaseCommand {
    @Override
    @Command(name = "tag")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        new TagsMenu(player.getDisplayName()).openMenu(player);
    }
}