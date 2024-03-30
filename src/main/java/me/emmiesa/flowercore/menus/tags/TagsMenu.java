package me.emmiesa.flowercore.menus.tags;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.menus.settings.button.SettingsButton;
import me.emmiesa.flowercore.menus.tags.button.TagsButton;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.tags.Tag;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.menu.Menu;
import me.emmiesa.flowercore.utils.menu.button.RefillGlassButton;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class TagsMenu extends Menu {

    private final RefillGlassButton refillGlassButton;
    private final String playerName;

    public TagsMenu(String playerName) {
        this.playerName = playerName;
        this.refillGlassButton = new RefillGlassButton(Material.STAINED_GLASS_PANE, 15);
    }

    @Override
    public String getTitle(Player player) {
        return FlowerCore.getInstance().getConfigHandler().getConfigByName("menus/tag-selector.yml").getString("title").replace("%player%", playerName);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        int slot = 10;

        for (Tag tag : FlowerCore.getInstance().getTagsManager().getTags()) {
            buttons.put(slot, new TagsButton(tag, playerName));
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
        FileConfiguration newsConfig = FlowerCore.getInstance().getConfigHandler().getConfigByName("menus/tag-selector.yml");
        if (newsConfig != null) {
            return newsConfig.getInt("size", 9 * 3);
        }

        return 9 * 3;
    }
}