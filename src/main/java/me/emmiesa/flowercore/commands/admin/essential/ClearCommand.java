package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class ClearCommand extends BaseCommand {

    @Override
    @Command(name = "clear", permission = "flowercore.staff")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.getArgs().length == 0) {
            if (isInventoryClear(player)) {
                player.sendMessage(CC.translate("&cYour inventory is already clear."));
                return;
            }

            player.getInventory().clear();
            player.sendMessage(CC.translate("&fYou've cleared &byour &finventory!"));

        } else if (command.getArgs().length == 1) {
            Player targetPlayer = player.getServer().getPlayer(command.getArgs()[0]);
            if (targetPlayer == null) {
                player.sendMessage(CC.translate("&fNo player matching &b" + command.getArgs()[0] + " &fis connected to this server."));
                return;
            }

            if (isInventoryClear(targetPlayer)) {
                player.sendMessage(CC.translate("&c" + targetPlayer.getDisplayName() + "'s inventory is already clear."));
                return;
            }

            targetPlayer.getInventory().clear();
            targetPlayer.sendMessage(CC.translate("&aYour inventory has been cleared by &r" + FlowerCore.getInstance().getPlayerManager().getRank(player.getUniqueId()).getPrefix() + player.getDisplayName() + "&c!"));
            player.sendMessage(CC.translate("&aYou've cleared " + FlowerCore.getInstance().getPlayerManager().getRank(targetPlayer.getUniqueId()).getPrefix() + targetPlayer.getDisplayName() + "&a's inventory!"));
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
