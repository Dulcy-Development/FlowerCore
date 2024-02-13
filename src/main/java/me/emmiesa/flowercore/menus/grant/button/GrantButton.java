package me.emmiesa.flowercore.menus.grant.button;

import lombok.AllArgsConstructor;
import me.emmiesa.flowercore.menus.grantconfirm.GrantConfirmMenu;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.item.ItemBuilder;
import me.emmiesa.flowercore.utils.menu.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

@AllArgsConstructor
public class GrantButton extends Button {
    private final Rank rank;
    private final String playerName;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(rank.getIcon()).name(rank.getDisplayName()).lore(Arrays.asList(
                CC.FLOWER_BAR_LONG,
                "    &f● Display name: &b" + rank.getDisplayName(),
                "    &f● Priority: &b" + rank.getPriority(),
                "    &f● Prefix: &b'" + rank.getPrefix() + "&b'",
                "    &f● Suffix: &b'" + rank.getSuffix() + "&b'",
                rank.isStaff() ? "    &f● Staff-Rank: &bYes" : "    &f● Staff-Rank: &bNo",
                rank.isDefaultRank() ? "    &f● Default-Rank: &bYes" : "    &f● Default-Rank: &bNo",
                "    &f● Appearance:",
                "       " + rank.getPrefix() + playerName + rank.getSuffix() + "&7: &fHi ❤",
                " ",
                "    &aClick to grant the &f" + rank.getDisplayName() + " &arank to &f" + playerName,
                CC.FLOWER_BAR_LONG)
        ).build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
        UUID playerToGrantUUID = Bukkit.getPlayerExact(playerName).getUniqueId();
        if (playerToGrantUUID != null) {
            new GrantConfirmMenu(playerToGrantUUID, rank, playerName).openMenu(player);
        } else {
            player.sendMessage(CC.translate("&cTarget player must be online to grant a rank."));
        }
    }
}
