package me.emmy.core.setting.menu;

import me.emmy.core.api.menu.Button;
import me.emmy.core.api.menu.Menu;
import me.emmy.core.api.menu.button.RefillGlassButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class SettingsMenu extends Menu {

    private final RefillGlassButton refillGlassButton;

    public SettingsMenu() {
        this.refillGlassButton = new RefillGlassButton(
                Material.STAINED_GLASS_PANE, 15
        );
    }

    @Override
    public String getTitle(Player player) {
        return "Settings Menu";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(10, new SettingsButton(Material.BOOK_AND_QUILL, "&b&lPrivate Messages",
                Arrays.asList(
                        "&8&m--------------------",
                        "&7&oSee private messages",
                        "&7&oor not.",
                        " ",
                        "&fStatus:"/*+ get the status + " "*/,
                        " ",
                        "&bClick to change!",
                        "&8&m--------------------"
                ))
        );

        buttons.put(11, new SettingsButton(Material.PAPER, "&b&lGlobal Chat",
                Arrays.asList(
                        "&8&m--------------------",
                        "&7&oSee global chat",
                        "&7&oor not.",
                        " ",
                        "&fStatus:"/*+ get the status + " "*/,
                        " ",
                        "&bClick to change!",
                        "&8&m--------------------"
                ))
        );

        buttons.put(12, new SettingsButton(Material.SHEARS, "&b&lToggle club chat",
                Arrays.asList(
                        "&8&m--------------------",
                        "&7&oSee club chat",
                        "&7&oor not.",
                        " ",
                        "&bClick to change!",
                        "&8&m--------------------"
                ))
        );

        buttons.put(13, new SettingsButton(Material.REDSTONE_BLOCK, "&c&lEmpty",
                Arrays.asList(
                        "&8&m--------------------",
                        "&c (!) Empty slot (!)",
                        "&8&m--------------------"
                ))
        );

        buttons.put(14, new SettingsButton(Material.REDSTONE_BLOCK, "&c&lEmpty",
                Arrays.asList(
                        "&8&m--------------------",
                        "&c (!) Empty slot (!)",
                        "&8&m--------------------"
                ))
        );

        buttons.put(15, new SettingsButton(Material.REDSTONE_BLOCK, "&c&lEmpty",
                Arrays.asList(
                        "&8&m--------------------",
                        "&c (!) Empty slot (!)",
                        "&8&m--------------------"
                ))
        );

        buttons.put(16, new SettingsButton(Material.WATCH, "&b&lAnnounce the network",
                Arrays.asList(
                        "&8&m--------------------",
                        "&7&oAnnounce your current",
                        "&7&oserver",
                        " ",
                        player.hasPermission("flowercore.command.announce") ? "&bClick to announce!" : "&cNo permission.",
                        "&8&m--------------------"
                ))
        );

        buttons.put(31, new SettingsButton(Material.DIAMOND, "&b&lDonator Settings",
                Arrays.asList(
                        "&8&m--------------------",
                        "&7&oDonator specific",
                        "&7&osettings...",
                        "&r ",
                        player.hasPermission("flowercore.donator") ? "&bClick to view!" : "&cNo permission.",
                        "&8&m--------------------"
                ))
        );

        for (int i = 0; i < getSize(); i++) {
            if (!buttons.containsKey(i)) {
                buttons.put(i, refillGlassButton);
            }
        }

        return buttons;
    }

    @Override
    public int getSize() {
        return 9 * 5;
    }
}