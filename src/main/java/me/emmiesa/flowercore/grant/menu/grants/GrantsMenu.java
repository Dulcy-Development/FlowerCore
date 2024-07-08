package me.emmiesa.flowercore.grant.menu.grants;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.grant.Grant;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.pagination.PaginatedMenu;
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
