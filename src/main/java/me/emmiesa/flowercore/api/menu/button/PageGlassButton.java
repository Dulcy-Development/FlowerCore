package me.emmiesa.flowercore.api.menu.button;

import me.emmiesa.flowercore.utils.item.ItemBuilder;
import me.emmiesa.flowercore.api.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PageGlassButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.STAINED_GLASS_PANE)
                .durability(11)
                .build();
    }
}
