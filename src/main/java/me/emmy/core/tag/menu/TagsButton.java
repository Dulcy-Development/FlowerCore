package me.emmy.core.tag.menu;

import lombok.AllArgsConstructor;
import me.emmy.core.FlowerCore;
import me.emmy.core.tag.Tag;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.utils.item.ItemBuilder;
import me.emmy.core.api.menu.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
@AllArgsConstructor
public class TagsButton extends Button {
    private final Tag tag;
    private final String playerName;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> customLore = FlowerCore.getInstance().getConfigHandler().getConfig("menus/tag-selector.yml").getStringList("tags-lore");
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
            FlowerCore.getInstance().getProfileRepository().resetTag(playerUUID);
            FlowerCore.getInstance().getMongoService().saveProfile(playerUUID);
            player.sendMessage(CC.translate("&aYour tag has been reset."));
            player.closeInventory();
            return;
        }

        boolean hasTagSelected = FlowerCore.getInstance().getProfileRepository().getTag(playerUUID) != null && FlowerCore.getInstance().getProfileRepository().getTag(playerUUID).equals(tag);

        if (hasTagSelected) {
            player.sendMessage(CC.translate("&cYou have already selected that tag!"));
            return;
        }

        FlowerCore.getInstance().getProfileRepository().setTag(playerUUID, tag);
        FlowerCore.getInstance().getMongoService().saveProfile(playerUUID);
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
        line = line.replace("{prefix}", FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).getPrefix());
        line = line.replace("{player_color}", FlowerCore.getInstance().getProfileRepository().getPlayerRankColor(playerUUID));
        boolean hasTagSelected = FlowerCore.getInstance().getProfileRepository().getTag(playerUUID) != null && FlowerCore.getInstance().getProfileRepository().getTag(playerUUID).equals(tag);

        if (hasTagSelected) {
            line = line.replace("{permission-status}", FlowerCore.getInstance().getConfigHandler().getConfig("menus/tag-selector.yml").getString("permission-lore.already-selected")
                    .replace("{tag_name}", tag.getName())
                    .replace("{tag_color}", tag.getColor())
            );
        } else {
            if (player.hasPermission("flowercore.tag." + tag.getName())) {
                line = line.replace("{permission-status}", FlowerCore.getInstance().getConfigHandler().getConfig("menus/tag-selector.yml").getString("permission-lore.has-permission")
                        .replace("{tag_name}", tag.getName())
                        .replace("{tag_color}", tag.getColor())
                );
            } else {
                line = line.replace("{permission-status}", FlowerCore.getInstance().getConfigHandler().getConfig("menus/tag-selector.yml").getString("permission-lore.no-permission")
                        .replace("{tag_name}", tag.getName())
                        .replace("{tag_color}", tag.getColor())
                );
            }
        }

        return line;
    }
}