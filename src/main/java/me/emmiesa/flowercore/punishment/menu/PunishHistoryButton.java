package me.emmiesa.flowercore.punishment.menu;

import lombok.AllArgsConstructor;
import me.emmiesa.flowercore.punishment.Punishment;
import me.emmiesa.flowercore.punishment.PunishmentType;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.item.ItemBuilder;
import me.emmiesa.flowercore.api.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


/**
 * @author Emmy
 * @project FlowerCore
 * @date 09/05/2024 - 22:01
 */
@AllArgsConstructor
public class PunishHistoryButton extends Button {

    private final Punishment punishment;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(getMaterial(punishment.getType()))
                .name("&c&l" + punishment.getType().toString()).
                lore(Arrays.asList(
                CC.FLOWER_BAR_LONG_RED,
                "     &f┃ IP: &c" + punishment.getIpAddress(),
                "     &f┃ By: &c" + punishment.getBy(),
                "     &f┃ Reason: &c" + punishment.getReason(),
                "     &f┃ Duration: &c" + punishment.getDuration(),
                "     &f┃ IP-Ban: &c" + (punishment.isIp() ? "true" : "false"),
                punishment.isActive() ? "     &f┃ Active? &aYes" : "     &f┃ Active? &cNo",
                CC.FLOWER_BAR_LONG_RED)
        ).build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {

        if (clickType == ClickType.MIDDLE || clickType == ClickType.RIGHT || clickType == ClickType.NUMBER_KEY || clickType == ClickType.DROP || clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT) {
            return;
        }

        if (punishment.getType().equals(PunishmentType.BAN)) {
            playSuccess(player);
        }
    }

    private Material getMaterial (PunishmentType punishmentType) {
        if (punishmentType.equals(PunishmentType.BAN)) {
            return Material.REDSTONE;
        }

        if (punishmentType.equals(PunishmentType.BLACKLIST)) {
            return Material.BARRIER;
        }

        if (punishmentType.equals(PunishmentType.MUTE)) {
            return Material.QUARTZ;
        }

        if (punishmentType.equals(PunishmentType.KICK)) {
            return Material.FEATHER;
        }

        if (punishmentType.equals(PunishmentType.WARN)) {
            return Material.COAL;
        }
        return null;
    }
}