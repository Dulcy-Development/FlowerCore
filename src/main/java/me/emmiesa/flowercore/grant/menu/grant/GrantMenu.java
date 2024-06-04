package me.emmiesa.flowercore.grant.menu.grant;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.pagination.PaginatedMenu;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class GrantMenu extends PaginatedMenu {

    private final String playerName;

    public GrantMenu(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return FlowerCore.getInstance().getConfigHandler().getConfigByName("menus/grant.yml").getString("title").replace("%player%", playerName);
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (Rank rank : FlowerCore.getInstance().getRanksManager().getRanks()) {
            buttons.put(buttons.size(), new GrantButton(rank, playerName));
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
