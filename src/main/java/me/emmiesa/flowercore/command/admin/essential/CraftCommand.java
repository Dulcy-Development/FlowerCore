package me.emmiesa.flowercore.command.admin.essential;

import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class CraftCommand extends BaseCommand {
    @Command(name = "craft", aliases = "opencraftingmenu", permission = "flower.command.craft")

    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        player.openWorkbench(player.getLocation(), true);
    }
}