package me.emmy.core.grant.menu.grant;

import lombok.AllArgsConstructor;
import me.emmy.core.FlowerCore;
import me.emmy.core.grant.menu.grantconfirm.GrantConfirmMenu;
import me.emmy.core.rank.Rank;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.utils.item.ItemBuilder;
import me.emmy.core.api.menu.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
@AllArgsConstructor
public class GrantButton extends Button {
    private final Rank rank;
    private final String playerName;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> customLore = FlowerCore.getInstance().getConfigHandler().getConfig("menus/grant.yml").getStringList("grant-lore");
        customLore.replaceAll(this::replacePlaceholders);

        return new ItemBuilder(rank.getIcon())
                .name(rank.getDisplayName())
                .lore(customLore)
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
        if (clickType == ClickType.MIDDLE || clickType == ClickType.RIGHT || clickType == ClickType.NUMBER_KEY || clickType == ClickType.DROP || clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT) {
            return;
        }

        UUID playerToGrantUUID = Bukkit.getPlayerExact(playerName).getUniqueId();
        if (playerToGrantUUID != null) {
            new GrantConfirmMenu(playerToGrantUUID, rank, playerName).openMenu(player);
        } else {
            player.sendMessage(CC.translate("&cTarget player must be online to grant a rank."));
        }
    }

    private String replacePlaceholders(String line) {
        UUID playerToGrantUUID = Bukkit.getPlayerExact(playerName).getUniqueId();
        line = line.replace("{flower_bar}", CC.FLOWER_BAR_LONG);
        line = line.replace("{display_name}", rank.getDisplayName());
        line = line.replace("{priority}", String.valueOf(rank.getPriority()));
        line = line.replace("{prefix}", rank.getPrefix());
        line = line.replace("{suffix}", rank.getSuffix());
        line = line.replace("{staff_rank}", rank.isStaff() ? "Yes" : "No");
        line = line.replace("{default_rank}", rank.isDefaultRank() ? "Yes" : "No");
        line = line.replace("{player_name}", playerName);
        line = line.replace("{player_color}", FlowerCore.getInstance().getProfileRepository().getPlayerRankColor(playerToGrantUUID));
        line = line.replace("{current_rank}", FlowerCore.getInstance().getProfileRepository().getRank(playerToGrantUUID).getDisplayName());
        return line;
    }
}
