package me.emmiesa.flowercore.menus.punishmenthistory;

import me.emmiesa.flowercore.menus.punishmenthistory.button.PunishhistoryButton;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.menu.Menu;
import me.emmiesa.flowercore.utils.menu.button.RefillGlassButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class PunishhistoryMenu extends Menu {
    private final String playerName;
    private final RefillGlassButton refillGlassButton;

    public PunishhistoryMenu(String playerName) {
        this.refillGlassButton = new RefillGlassButton(
                Material.STAINED_GLASS_PANE, 15
        );

        this.playerName = playerName;
    }

    @Override
    public String getTitle(Player player) {
        return "Settings Menu";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(11, new PunishhistoryButton(Material.REDSTONE, "&b&lBan History of " + playerName, playerName,
                Arrays.asList(
                        "&8&m--------------------",
                        "&7&oView the ban history",
                        "&7&oof the player.",
                        " ",
                        "&fAmount of bans: -",
                        " ",
                        "&bClick to view!",
                        "&8&m--------------------"
                ))
        );

        buttons.put(13, new PunishhistoryButton(Material.QUARTZ, "&b&lBlacklist History of " + playerName, playerName,
                Arrays.asList(
                        "&8&m--------------------",
                        "&7&oView the blacklist history",
                        "&7&oof the player.",
                        " ",
                        "&fAmount of blacklist: -",
                        " ",
                        "&bClick to view!",
                        "&8&m--------------------"
                ))
        );

        buttons.put(15, new PunishhistoryButton(Material.COAL, "&b&lMute History of " + playerName, playerName,
                Arrays.asList(
                        "&8&m--------------------",
                        "&7&oView the mute history",
                        "&7&oof the player.",
                        " ",
                        "&fAmount of mute: -",
                        " ",
                        "&bClick to view!",
                        "&8&m--------------------"
                ))
        );

        buttons.put(31, new PunishhistoryButton(Material.EMERALD, "&b&lKick History of " + playerName, playerName,
                Arrays.asList(
                        "&8&m--------------------",
                        "&7&oView the kick history",
                        "&7&oof the player.",
                        " ",
                        "&fAmount of kick: -",
                        " ",
                        "&bClick to view!",
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