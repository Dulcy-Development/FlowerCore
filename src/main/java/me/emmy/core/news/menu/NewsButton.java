package me.emmy.core.news.menu;

import me.emmy.core.utils.item.ItemBuilder;
import me.emmy.core.api.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class NewsButton extends Button {

	private Material material;
	private short data;
	private String displayName;
	private List<String> lore;
	private String command;

	/**
	 * Constructor for the NewsButton
	 *
	 * @param material the material of the button
	 * @param data the data of the button
	 * @param displayName the display name of the button
	 * @param lore the lore of the button
	 * @param command the command to execute
	 */
	public NewsButton(Material material, short data, String displayName, List<String> lore, String command) {
		this.material = material;
		this.data = data;
		this.displayName = displayName;
		this.lore = lore;
		this.command = command;
	}

	@Override
	public ItemStack getButtonItem(Player player) {
		ItemStack item = new ItemStack(material);
		item.setDurability(data);
		ItemMeta meta = item.getItemMeta();
		if (meta != null) {
			meta.setDisplayName(displayName);
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		return new ItemBuilder(item).hideMeta().build();
	}

	@Override
	public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {

		if (clickType == ClickType.MIDDLE || clickType == ClickType.RIGHT || clickType == ClickType.NUMBER_KEY || clickType == ClickType.DROP || clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT) {
			return;
		}

		executeCommand(player);
	}

	private void executeCommand(Player player) {
		if (command != null && !command.isEmpty()) {
			player.performCommand(command);
		} else {
			player.sendMessage("Clicked slot #");
		}
	}
}
