package me.emmy.core.command.admin.essential;

import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class CraftCommand extends BaseCommand {
    @Override
    @Command(name = "craft", aliases = "opencraftingmenu", permission = "flower.command.craft")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        player.openWorkbench(player.getLocation(), true);
    }
}