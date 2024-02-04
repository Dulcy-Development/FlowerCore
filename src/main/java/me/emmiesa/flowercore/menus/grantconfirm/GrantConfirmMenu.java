package me.emmiesa.flowercore.menus.grantconfirm;

import me.emmiesa.flowercore.menus.grantconfirm.button.GrantConfirmButton;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.menu.Menu;
import me.emmiesa.flowercore.utils.menu.button.RefillGlassButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GrantConfirmMenu extends Menu {
    private final UUID playerToGrantUUID;
    private final Rank rank;
    private final String playerName;
    private final RefillGlassButton refillGlassButton;

    public GrantConfirmMenu(UUID playerToGrantUUID, Rank rank, String playerName) {
        this.playerToGrantUUID = playerToGrantUUID;
        this.rank = rank;
        this.playerName = playerName;
        this.refillGlassButton = new RefillGlassButton(Material.STAINED_GLASS_PANE, 15);
    }

    @Override
    public String getTitle(Player player) {
        return CC.translate("Confirm Grant for " + playerName);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(11, new GrantConfirmButton(playerName, playerToGrantUUID, rank, Material.DIAMOND_BLOCK, "&b&lConfirm", Arrays.asList(
                " &fTo be granted Player &b" + playerName,
                " &fTo be granted Rank: &b" + rank.getDisplayName(),
                rank.isStaff() ? " &fRank Type: &bStaff" : " &fRank Type: &bNon-Staff/Donator",
                " ",
                "&bClick to confirm the grant!"
        ), true));

        buttons.put(15, new GrantConfirmButton(playerName, playerToGrantUUID, rank, Material.REDSTONE_BLOCK, "&c&lCancel", Arrays.asList(
                " ",
                "&cClick to cancel the grant!",
                " "
        ), false));

        buttons.put(22, new GrantConfirmButton(playerName, playerToGrantUUID, rank, Material.PAPER, "&e&lChanged your mind?", Arrays.asList(
                " ",
                "&eClick to select another rank for " + playerName + "&e!",
                " "
        ), false));

        for (int i = 0; i < getSize(); i++) {
            if (!buttons.containsKey(i)) {
                buttons.put(i, refillGlassButton);
            }
        }

        return buttons;
    }

    @Override
    public int getSize() {
        return 9 * 3;
    }
}
