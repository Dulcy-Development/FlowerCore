package me.emmiesa.flowercore.menus.tags.button;

import lombok.AllArgsConstructor;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.menus.grantconfirm.GrantConfirmMenu;
import me.emmiesa.flowercore.tags.Tag;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.item.ItemBuilder;
import me.emmiesa.flowercore.utils.menu.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

@AllArgsConstructor
public class TagsButton extends Button {
    private final Tag tag;
    private final String playerName;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> customLore = FlowerCore.getInstance().getConfigHandler().getConfigByName("menus/tag-selector.yml").getStringList("tags-lore");
        customLore.replaceAll(this::replacePlaceholders);

        return new ItemBuilder(tag.getIcon())
                .name(tag.getColor() + tag.getName() + " &r┃ " + tag.getDisplayName())
                .lore(customLore)
                .build();

    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
        if (clickType == ClickType.MIDDLE || clickType == ClickType.NUMBER_KEY || clickType == ClickType.DROP || clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT) {
            return;
        }

        if (clickType == ClickType.RIGHT) {
            UUID playerUUID = Bukkit.getPlayerExact(playerName).getUniqueId();
            FlowerCore.getInstance().getPlayerManager().resetTag(playerUUID);
            player.sendMessage(CC.translate("&aYour tag has been reset."));
            player.closeInventory();
            return;
        }

        UUID playerToGrantUUID = Bukkit.getPlayerExact(playerName).getUniqueId();
        FlowerCore.getInstance().getPlayerManager().setTag(playerToGrantUUID, tag);

        player.sendMessage(CC.translate("&aYou've selected the " + tag.getColor() + tag.getName() + " &atag!"));
    }

    private String replacePlaceholders(String line) {
        UUID playerUUID = Bukkit.getPlayerExact(playerName).getUniqueId();
        line = line.replace("{flower_bar}", CC.FLOWER_BAR_LONG);
        line = line.replace("{tag_format}", tag.getDisplayName());
        line = line.replace("{tag_color}", tag.getColor());
        line = line.replace("{tag_name}", tag.getName());
        line = line.replace("{player_name}", playerName);
        line = line.replace("{prefix}", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix());
        line = line.replace("{player_color}", FlowerCore.getInstance().getPlayerManager().getPlayerRankColor(playerUUID));
        return line;
    }
}
