package me.emmiesa.flowercore.menus.ranklist;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.menus.ranklist.button.RankListButton;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.menu.Menu;
import me.emmiesa.flowercore.utils.menu.button.RefillGlassButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RankListMenu extends Menu {

    private final RefillGlassButton refillGlassButton;
    //private BackButton backButton;

    public RankListMenu() {
        this.refillGlassButton = new RefillGlassButton(Material.STAINED_GLASS_PANE, 14);
    }

    @Override
    public String getTitle(Player player) {
        return "Rank list";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        //buttons.put(0, backButton);

        int slot = 0;

        for (Rank rank : FlowerCore.getInstance().getRanksManager().getRanks()) {
            buttons.put(slot, new RankListButton(rank));
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
        return 9 * 5;
    }
}