package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MoreCommand extends BaseCommand {
    @Command(name = "more", permission = "flower.command.more", aliases = {"stackitem"})

    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        ItemStack item = player.getItemInHand();

        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(CC.translate("&cWhat do you want me to stack? Nothing?"));
            return;
        }
        if (item.getAmount() >= 64) {
            player.sendMessage(CC.translate("&cThis is already a stack of 64."));
        return;
        }

        item.setAmount(64);
        player.updateInventory();
        player.sendMessage(CC.translate("&fHere you go."));
    }
}
