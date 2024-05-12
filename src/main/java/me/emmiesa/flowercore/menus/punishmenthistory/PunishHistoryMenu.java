package me.emmiesa.flowercore.menus.punishmenthistory;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.menus.punishmenthistory.button.PunishHistoryButton;
import me.emmiesa.flowercore.punishments.Punishment;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.menu.Menu;
import me.emmiesa.flowercore.utils.pagination.PaginatedMenu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 09/05/2024 - 21:54
 */

public class PunishHistoryMenu extends PaginatedMenu {
    private final String playerName;

    public PunishHistoryMenu(String playerName) {
        this.playerName = playerName;
    }
    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Punishment History of " + playerName;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        int slot = 0;

        for (Punishment punishment : FlowerCore.getInstance().getPlayerManager().getProfile(player.getUniqueId()).getPunishments()) {
            buttons.put(slot, new PunishHistoryButton(punishment));
            slot++;
        }

        return buttons;
    }

    @Override
    public int getSize() {
        return 9 * 5;
    }
}