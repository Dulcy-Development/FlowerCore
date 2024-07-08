package me.emmiesa.flowercore.rank.menu;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.pagination.PaginatedMenu;
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
