package me.emmiesa.flowercore.menus.grant.grant;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.menus.grant.grant.button.GrantButton;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.menu.Menu;
import me.emmiesa.flowercore.utils.menu.button.RefillGlassButton;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GrantMenu extends Menu {

	private final RefillGlassButton refillGlassButton;

	public GrantMenu() {
		this.refillGlassButton = new RefillGlassButton(Material.STAINED_GLASS_PANE, 15);
	}

	@Override
	public String getTitle(Player player) {
		return "Grant " + player.getDisplayName();
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = new HashMap<>();

		int slot = 10;

		for (Rank rank : FlowerCore.getInstance().getRanksManager().getRanks()) {
			buttons.put(slot, new GrantButton(rank));
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