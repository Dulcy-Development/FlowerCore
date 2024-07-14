package me.emmy.core.socials.menu;

import me.emmy.core.api.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 04/06/2024 - 20:53
 */
public class SocialsButton extends Button {
    @Override
    public ItemStack getButtonItem(Player player) {
        return null;
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (clickType != ClickType.LEFT) {
            return;
        }

        playNeutral(player);
    }
}
