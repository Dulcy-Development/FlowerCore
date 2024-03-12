package me.emmiesa.flowercore.menus.ranklist.button;

import lombok.AllArgsConstructor;
import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.item.ItemBuilder;
import me.emmiesa.flowercore.utils.menu.Button;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@AllArgsConstructor
public class RankListButton extends Button {

    private final Rank rank;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(rank.getIcon()).name(rank.getDisplayName()).lore(Arrays.asList(
                CC.FLOWER_BAR,
                "     &f┃ Display name: &b" + rank.getDisplayName(),
                "     &f┃ Priority: &b" + rank.getPriority(),
                "     &f┃ Prefix: &b'" + rank.getPrefix() + "'",
                "     &f┃ Suffix: &b'" + rank.getSuffix() + "'",
                rank.isStaff() ? "     &f┃ Staff-Rank? &bYes" : "     &f┃ Staff-Rank? &bNo",
                rank.isDefaultRank() ? "     &f┃ Default-Rank? &bYes" : "     &f┃ Default-Rank? &bNo",
                CC.FLOWER_BAR)
        ).build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {

        if (clickType == ClickType.MIDDLE || clickType == ClickType.RIGHT || clickType == ClickType.NUMBER_KEY || clickType == ClickType.DROP || clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT) {
            return;
        }

        player.playSound(player.getLocation(), Sound.STEP_GRASS, 2.0F, 1.5F);
    }
}