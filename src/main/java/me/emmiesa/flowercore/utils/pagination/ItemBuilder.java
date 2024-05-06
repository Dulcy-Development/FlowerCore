package me.emmiesa.flowercore.utils.pagination;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 01/04/2024 - 00:12
 */

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder implements Listener {

    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder itemAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder displayName(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();

        if (lore == null) {
            lore = new ArrayList<>();
        }

        lore.add(ChatColor.translateAlternateColorCodes('&', name));
        meta.setLore(lore);

        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder lore(String... lore) {
        List<String> toSet = new ArrayList<>();
        ItemMeta meta = itemStack.getItemMeta();

        for (String string : lore) {
            toSet.add(ChatColor.translateAlternateColorCodes('&', string));
        }

        meta.setLore(toSet);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        List<String> toSet = new ArrayList<>();
        ItemMeta meta = itemStack.getItemMeta();

        for (String string : lore) {
            toSet.add(ChatColor.translateAlternateColorCodes('&', string));
        }

        meta.setLore(toSet);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder durability(int durability) {
        itemStack.setDurability((short) durability);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment) {
        itemStack.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemBuilder type(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemBuilder clearLore() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.setLore(new ArrayList<>());
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder clearEnchantments() {
        for (Enchantment e : itemStack.getEnchantments().keySet()) {
            itemStack.removeEnchantment(e);
        }

        return this;
    }

    public ItemBuilder clearFlags() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.values());

        itemStack.setItemMeta(itemMeta);

        return this;
    }


    public ItemStack build() {
        return itemStack;
    }
}