package me.emmy.core.rank.menu;

import me.emmy.core.FlowerCore;
import me.emmy.core.rank.Rank;
import me.emmy.core.api.menu.Button;
import me.emmy.core.api.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class RankListMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Rank list";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        int slot = 0;

        for (Rank rank : FlowerCore.getInstance().getRankRepository().getRanks()) {
            buttons.put(slot, new RankListButton(rank));
            slot++;
        }

        return buttons;
    }

    @Override
    public int getSize() {
        return 9 * 5;
    }
}
