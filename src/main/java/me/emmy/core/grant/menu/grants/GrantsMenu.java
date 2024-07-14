package me.emmy.core.grant.menu.grants;

import me.emmy.core.FlowerCore;
import me.emmy.core.grant.Grant;
import me.emmy.core.api.menu.Button;
import me.emmy.core.api.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 03/06/2024 - 20:33
 */
public class GrantsMenu extends PaginatedMenu {

    private final String playerName;
    private final UUID targetUUID;

    /**
     * Constructor for the GrantsMenu
     *
     * @param playerName the name of the player
     * @param targetUUID the UUID of the player
     */
    public GrantsMenu(String playerName, UUID targetUUID) {
        this.playerName = playerName;
        this.targetUUID = targetUUID;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return playerName + "'s Grants";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        int slot = 0;

        for (Grant grant : FlowerCore.getInstance().getProfileRepository().getProfile(targetUUID).getGrants()) {
            buttons.put(slot++, new GrantsButton(grant, playerName, targetUUID));
        }

        return buttons;
    }

    @Override
    public int getSize() {
        return 9 * 3;
    }
}
