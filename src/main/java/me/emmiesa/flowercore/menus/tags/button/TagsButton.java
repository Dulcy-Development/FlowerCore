package me.emmiesa.flowercore.menus.tags.button;

import lombok.AllArgsConstructor;
import me.emmiesa.flowercore.FlowerCore;
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
        List<String> customLore = FlowerCore.getINSTANCE().getConfigHandler().getConfigByName("menus/tag-selector.yml").getStringList("tags-lore");
        customLore.replaceAll(line -> replacePlaceholders(line, player));

        return new ItemBuilder(tag.getIcon())
                .name("&fTag: &r" + tag.getColor() + tag.getName())
                .lore(customLore)
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
        if (clickType == ClickType.MIDDLE || clickType == ClickType.NUMBER_KEY || clickType == ClickType.DROP || clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT) {
            return;
        }

        UUID playerUUID = Bukkit.getPlayerExact(playerName).getUniqueId();

        if (!player.hasPermission("flowercore.tag." + tag.getName())) {
            return;
        }

        if (clickType == ClickType.RIGHT) {
            FlowerCore.getINSTANCE().getPlayerManager().resetTag(playerUUID);
            FlowerCore.getINSTANCE().getMongoManager().saveProfile(playerUUID);
            player.sendMessage(CC.translate("&aYour tag has been reset."));
            player.closeInventory();
            return;
        }

        boolean hasTagSelected = FlowerCore.getINSTANCE().getPlayerManager().getTag(playerUUID) != null && FlowerCore.getINSTANCE().getPlayerManager().getTag(playerUUID).equals(tag);

        if (hasTagSelected) {
            player.sendMessage(CC.translate("&cYou have already selected that tag!"));
            return;
        }

        FlowerCore.getINSTANCE().getPlayerManager().setTag(playerUUID, tag);
        FlowerCore.getINSTANCE().getMongoManager().saveProfile(playerUUID);
        player.sendMessage(CC.translate("&aYou've selected the " + tag.getColor() + tag.getName() + " &atag!"));
        player.closeInventory();
    }

    private String replacePlaceholders(String line, Player player) {
        UUID playerUUID = Bukkit.getPlayerExact(playerName).getUniqueId();
        line = line.replace("{flower_bar}", CC.FLOWER_BAR_LONG);
        line = line.replace("{tag_format}", tag.getDisplayName());
        line = line.replace("{tag_color}", tag.getColor());
        line = line.replace("{tag_name}", tag.getName());
        line = line.replace("{player_name}", playerName);
        line = line.replace("{prefix}", FlowerCore.getINSTANCE().getPlayerManager().getRank(playerUUID).getPrefix());
        line = line.replace("{player_color}", FlowerCore.getINSTANCE().getPlayerManager().getPlayerRankColor(playerUUID));
        boolean hasTagSelected = FlowerCore.getINSTANCE().getPlayerManager().getTag(playerUUID) != null && FlowerCore.getINSTANCE().getPlayerManager().getTag(playerUUID).equals(tag);

        if (hasTagSelected) {
            line = line.replace("{permission-status}", FlowerCore.getINSTANCE().getConfigHandler().getConfigByName("menus/tag-selector.yml").getString("permission-lore.already-selected")
                    .replace("{tag_name}", tag.getName())
                    .replace("{tag_color}", tag.getColor())
            );
        } else {
            if (player.hasPermission("flowercore.tag." + tag.getName())) {
                line = line.replace("{permission-status}", FlowerCore.getINSTANCE().getConfigHandler().getConfigByName("menus/tag-selector.yml").getString("permission-lore.has-permission")
                        .replace("{tag_name}", tag.getName())
                        .replace("{tag_color}", tag.getColor())
                );
            } else {
                line = line.replace("{permission-status}", FlowerCore.getINSTANCE().getConfigHandler().getConfigByName("menus/tag-selector.yml").getString("permission-lore.no-permission")
                        .replace("{tag_name}", tag.getName())
                        .replace("{tag_color}", tag.getColor())
                );
            }
        }

        return line;
    }
}