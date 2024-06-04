package me.emmiesa.flowercore.socials.menu;

import me.emmiesa.flowercore.utils.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 04/06/2024 - 20:53
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
