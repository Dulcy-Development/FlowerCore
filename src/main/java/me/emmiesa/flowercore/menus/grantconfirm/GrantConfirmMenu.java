package me.emmiesa.flowercore.menus.grantconfirm;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.menus.grantconfirm.button.GrantConfirmButton;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.menu.Menu;
import me.emmiesa.flowercore.utils.menu.button.RefillGlassButton;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

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
        return FlowerCore.getINSTANCE().getConfigHandler().getConfigByName("menus/grant-confirm.yml").getString("title").replace("%player%", playerName);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        FileConfiguration config = FlowerCore.getINSTANCE().getConfigHandler().getConfigByName("menus/grant-confirm.yml");

        String confirmTitle = config.getString("confirm-button.title", "&b&lConfirm");
        String confirmMaterialStr = config.getString("confirm-button.material", "DIAMOND_BLOCK");
        Material confirmMaterial = Material.matchMaterial(confirmMaterialStr);
        List<String> confirmLore = config.getStringList("confirm-button.lore");
        String staff = FlowerCore.getINSTANCE().getConfigHandler().getConfigByName("menus/grant-confirm.yml").getString("confirm-button.placeholder.staff");
        String nonStaff = FlowerCore.getINSTANCE().getConfigHandler().getConfigByName("menus/grant-confirm.yml").getString("confirm-button.placeholder.non-staff");
        confirmLore.replaceAll(line -> line.replace("{player_name}", playerName).replace("{rank}", rank.getDisplayName()).replace("{rank-type}", rank.isStaff() ? staff : nonStaff));

        String cancelTitle = config.getString("cancel-button.title", "&c&lCancel");
        String cancelMaterialStr = config.getString("cancel-button.material", "REDSTONE_BLOCK");
        Material cancelMaterial = Material.matchMaterial(cancelMaterialStr);
        List<String> cancelLore = config.getStringList("cancel-button.lore");

        String changeTitle = config.getString("change-button.title", "&e&lChanged your mind?");
        String changeMaterialStr = config.getString("change-button.material", "PAPER");
        Material changeMaterial = Material.matchMaterial(changeMaterialStr);
        List<String> changeLore = config.getStringList("change-button.lore");
        changeLore.replaceAll(line -> line.replace("{player_name}", playerName));

        buttons.put(config.getInt("confirm-button.slot", 11), new GrantConfirmButton(playerName, playerToGrantUUID, rank, confirmMaterial, confirmTitle, confirmLore, true));
        buttons.put(config.getInt("cancel-button.slot", 15), new GrantConfirmButton(playerName, playerToGrantUUID, rank, cancelMaterial, cancelTitle, cancelLore, false));
        buttons.put(config.getInt("change-button.slot", 22), new GrantConfirmButton(playerName, playerToGrantUUID, rank, changeMaterial, changeTitle, changeLore, false));

        for (int i = 0; i < getSize(); i++) {
            if (!buttons.containsKey(i)) {
                buttons.put(i, refillGlassButton);
            }
        }

        return buttons;
    }


    @Override
    public int getSize() {
        FileConfiguration newsConfig = FlowerCore.getINSTANCE().getConfigHandler().getConfigByName("menus/grant-confirm.yml");
        if (newsConfig != null) {
            return newsConfig.getInt("size", 9 * 3);
        }

        return 9 * 3;
    }
}
