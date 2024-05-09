package me.emmiesa.flowercore.menus.punishmenthistory.button;

import lombok.AllArgsConstructor;
import me.emmiesa.flowercore.utils.item.ItemBuilder;
import me.emmiesa.flowercore.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;


/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 *
 * @author Elb1to
 * @since 11/11/2023
 */
@AllArgsConstructor
public class PunishhistoryButton extends Button {

    private final Material icon;
    private final String displayName;
    private final String playerName;
    private List<String> lore;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(icon).name(displayName).lore(lore).build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {

        if (clickType == ClickType.MIDDLE || clickType == ClickType.RIGHT || clickType == ClickType.NUMBER_KEY || clickType == ClickType.DROP || clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT) {
            return;
        }

        if (icon.name().contains("BOOK_AND_QUILL")) {
            playSuccess(player);
        }
    }
    //new ServersMenu().openMenu(player);
    //playNeutral(player);
}