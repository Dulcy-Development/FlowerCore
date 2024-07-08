package me.emmiesa.flowercore.command.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class MoreCommand extends BaseCommand {
    @Override
    @Command(name = "more", permission = "flower.command.more", aliases = {"stackitem"})
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        ItemStack item = player.getItemInHand();

        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("more-command.no-item-held")));
            return;
        }
        if (item.getAmount() >= 64) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("more-command.already-a-stack")));
            return;
        }

        item.setAmount(64);
        player.updateInventory();
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("more-command.given")));
    }
}