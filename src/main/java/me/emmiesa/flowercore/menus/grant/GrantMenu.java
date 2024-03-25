package me.emmiesa.flowercore.menus.grant;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.menus.grant.button.GrantButton;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.menu.Menu;
import me.emmiesa.flowercore.utils.menu.button.RefillGlassButton;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class GrantMenu extends Menu {

    private final RefillGlassButton refillGlassButton;
    private final String playerName;

    public GrantMenu(String playerName) {
        this.playerName = playerName;
        this.refillGlassButton = new RefillGlassButton(Material.STAINED_GLASS_PANE, 15);
    }

    @Override
    public String getTitle(Player player) {
        return FlowerCore.getInstance().getConfigHandler().getConfigByName("menus/grant.yml").getString("title").replace("%player%", playerName);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        int slot = 10;

        for (Rank rank : FlowerCore.getInstance().getRanksManager().getRanks()) {
            buttons.put(slot, new GrantButton(rank, playerName));
            slot++;
        }

        for (int i = 0; i < getSize(); i++) {
            if (!buttons.containsKey(i)) {
                buttons.put(i, refillGlassButton);
            }
        }

        return buttons;
    }

    @Override
    public int getSize() {
        FileConfiguration newsConfig = FlowerCore.getInstance().getConfigHandler().getConfigByName("menus/grant.yml");
        if (newsConfig != null) {
            return newsConfig.getInt("size", 9 * 3);
        }

        return 9 * 3;
    }
}