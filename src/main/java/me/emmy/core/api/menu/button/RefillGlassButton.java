package me.emmy.core.api.menu.button;

import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */

public class RefillGlassButton extends Button {

    private final Material material;
    private final short data;

    public RefillGlassButton(Material material, int data) {
        this.material = material;
        this.data = (short) data;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemStack itemStack = new ItemStack(material, 1, data);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(CC.translate(" "));

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
        // Handle the refill glass button click event if needed
    }
}
