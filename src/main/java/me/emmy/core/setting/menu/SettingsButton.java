package me.emmy.core.setting.menu;

import lombok.AllArgsConstructor;
import me.emmy.core.locale.Locale;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.utils.item.ItemBuilder;
import me.emmy.core.api.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;


/**
 * @author Emmy
 * @project FlowerCore
 * @date 11/11/2023
 * @credit Elb1to
 */
@AllArgsConstructor
public class SettingsButton extends Button {

    private final Material icon;
    private final String displayName;
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
            player.performCommand("tpm");
        } else if (icon.name().contains("PAPER")) {
            playSuccess(player);
            player.performCommand("tgc");
        } else if (icon.name().contains("SHEARS")) {
            playSuccess(player);
            player.performCommand("toggleclubchat");
        } else if (icon.name().contains("WATCH")) {
            if (player.hasPermission("flowercore.donator")) {
                player.performCommand("announce");
            }
            if (!player.hasPermission("flowercore.donator")) {
                playerClickSound(player);
                player.closeInventory();
                player.sendMessage(CC.translate(Locale.NO_PERM));
            }
        } else if (icon.name().contains("REDSTONE_BLOCK")) {
            playerClickSound(player);
            player.sendMessage(CC.translate("&cEmpty!"));
        } else if (icon.name().contains("DIAMOND")) {
            if (player.hasPermission("flowercore.command.announce")) {
                playSuccess(player);
                player.performCommand("donatorsettings");
            }
            if (!player.hasPermission("flowercore.command.announce")) {
                playerClickSound(player);
                player.closeInventory();
                player.sendMessage(CC.translate(Locale.NO_PERM));
            }
        } else {
            player.sendMessage(CC.translate("&7&lClicked slot #" + slot));
            playSuccess(player);
        }
    }
    //new ServersMenu().openMenu(player);
    //playNeutral(player);
}