package me.emmiesa.flowercore.menus.news;

import me.clip.placeholderapi.PlaceholderAPI;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.menus.news.button.NewsButton;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.menu.Button;
import me.emmiesa.flowercore.utils.menu.Menu;
import me.emmiesa.flowercore.utils.menu.button.RefillGlassButton;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Emmy
 * Project: FlowerHub
 * GitHub: https://github.com/Emmiesa
 */

public class NewsMenu extends Menu {

	private final RefillGlassButton refillGlassButton;

	public NewsMenu() {
		this.refillGlassButton = new RefillGlassButton(
				Material.STAINED_GLASS_PANE,
				FlowerCore.getInstance().getConfig("settings.yml").getInt("menus.news.refill-glass.data", 15)
		);
	}

	@Override
	public String getTitle(Player player) {
		return CC.translate(FlowerCore.getInstance().getConfig("settings.yml").getString("menus.news.title"));
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = new HashMap<>();

		ConfigurationSection serversSection = FlowerCore.getInstance().getConfig("settings.yml").getConfigurationSection("menus.news.servers");

		if (serversSection != null) {
			for (String serverKey : serversSection.getKeys(false)) {
				ConfigurationSection serverSection = serversSection.getConfigurationSection(serverKey);

				if (serverSection != null) {
					int slot = serverSection.getInt("slot", 0);
					Material materialType = Material.matchMaterial(serverSection.getString("material", "STONE"));
					String name = CC.translate(serverSection.getString("name", "&c&lInvalid"));
					List<String> lore = serverSection.getStringList("lore").stream()
							.map(line -> CC.translate(PlaceholderAPI.setPlaceholders(player, line)))
							.collect(Collectors.toList());
					int data = serverSection.getInt("data", 0);
					Material material = new MaterialData(materialType, (byte) data).toItemStack().getType();

					buttons.put(slot, new NewsButton(material, (short) data, name, lore, serverSection.getString("command")));
				}
			}
		}

		// Add refill glass button
		ConfigurationSection refillGlassSection = FlowerCore.getInstance().getConfig("settings.yml").getConfigurationSection("menus.news.refill-glass");
		if (refillGlassSection != null && refillGlassSection.getBoolean("enabled", true)) {
			List<String> refillSlots = refillGlassSection.getStringList("slots");
			for (String refillSlot : refillSlots) {
				int slot = Integer.parseInt(refillSlot);
				buttons.put(slot, refillGlassButton);
			}
		}

		return buttons;
	}

	@Override
	public int getSize() {
		ConfigurationSection menuSection = FlowerCore.getInstance().getConfig("settings.yml").getConfigurationSection("menus.news");

		if (menuSection != null && menuSection.contains("size")) {
			return menuSection.getInt("size", 9 * 3);
		}

		return 9 * 3;
	}
}
