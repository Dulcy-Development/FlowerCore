package me.emmiesa.flowercore.command.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class ClearCommand extends BaseCommand {
    @Override
    @Command(name = "clear", permission = "flower.command.clear")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.getArgs().length == 0) {
            if (isInventoryClear(player)) {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("clear.already-clear")));
                return;
            }

            player.getInventory().clear();
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("clear.cleared")));

        } else if (command.getArgs().length == 1) {
            Player targetPlayer = player.getServer().getPlayer(command.getArgs()[0]);
            if (targetPlayer == null) {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("clear.target-not-found")).replace("%target%", command.getArgs()[0]));
                return;
            }

            if (isInventoryClear(targetPlayer)) {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("clear.target-already-clear")).replace("%target%", targetPlayer.getDisplayName()));
                return;
            }

            targetPlayer.getInventory().clear();
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("clear.cleared-by"))
                    .replace("%player%", targetPlayer.getDisplayName())
                    .replace("%player-color%", CC.translate(FlowerCore.getInstance().getProfileRepository().getRank(player.getUniqueId()).getColor())));

            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("clear.target-cleared"))
                    .replace("%target%", targetPlayer.getDisplayName())
                    .replace("%target-color%", CC.translate(FlowerCore.getInstance().getProfileRepository().getRank(targetPlayer.getUniqueId()).getColor())));
        }
    }

    private boolean isInventoryClear(Player player) {
        PlayerInventory inventory = player.getInventory();

        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                return false;
            }
        }

        for (ItemStack item : inventory.getArmorContents()) {
            if (item != null && item.getType() != Material.AIR) {
                return false;
            }
        }
        return true;
    }
}
