package me.emmiesa.flowercore.socials.menu;

import me.emmiesa.flowercore.api.menu.Button;
import me.emmiesa.flowercore.api.menu.Menu;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Map;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 04/06/2024 - 20:53
 */
public class SocialsMenu extends Menu {
    @Override
    public String getTitle(Player player) {
        return "";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        return Collections.emptyMap();
    }
}
