package me.emmiesa.flowercore.news.menu;

import me.clip.placeholderapi.PlaceholderAPI;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.menu.Button;
import me.emmiesa.flowercore.api.menu.Menu;
import me.emmiesa.flowercore.api.menu.button.RefillGlassButton;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class NewsMenu extends Menu {

	private final RefillGlassButton refillGlassButton;

	public NewsMenu() {
		this.refillGlassButton = new RefillGlassButton(
				Material.STAINED_GLASS_PANE,
				FlowerCore.getInstance().getConfigHandler().getConfigByName("menus/news.yml").getInt("refill-glass.data", 15)
		);
	}

	@Override
	public String getTitle(Player player) {
		return CC.translate(FlowerCore.getInstance().getConfigHandler().getConfigByName("menus/news.yml").getString("title"));
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = new HashMap<>();

		ConfigurationSection serversSection = FlowerCore.getInstance().getConfigHandler().getConfigByName("menus/news.yml").getConfigurationSection("items");

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
		ConfigurationSection refillGlassSection = FlowerCore.getInstance().getConfigHandler().getConfigByName("menus/news.yml").getConfigurationSection("refill-glass");
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
		FileConfiguration newsConfig = FlowerCore.getInstance().getConfigHandler().getConfigByName("menus/news.yml");
		if (newsConfig != null) {
			return newsConfig.getInt("size", 9 * 3);
		}

		return 9 * 3;
	}
}
