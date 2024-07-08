package me.emmiesa.flowercore.tag.menu;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.tag.Tag;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.pagination.PaginatedMenu;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TagsMenu extends PaginatedMenu {

    private final String playerName;

    /**
     * Constructor
     *
     * @param playerName Name of the player
     */
    public TagsMenu(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return FlowerCore.getInstance().getConfigHandler().getConfigByName("menus/tag-selector.yml").getString("title").replace("%player%", playerName);
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (Tag tag : FlowerCore.getInstance().getTagRepository().getTags()) {
            buttons.put(buttons.size(), new TagsButton(tag, playerName));
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
