package me.emmiesa.flowercore.command.admin.essential;

import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
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