package me.emmiesa.flowercore.menus.settings.button;

import lombok.AllArgsConstructor;
import me.emmiesa.flowercore.Lang;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.item.ItemBuilder;
import me.emmiesa.flowercore.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Elb1to
 * @since 11/11/2023
 */
@AllArgsConstructor
public class SettingsButton extends Button {

	private final Material icon;
	private final String displayName;
	private List<String> lore;

	@Override
	public ItemStack getButtonItem(Player player) {
		return new ItemBuilder(icon).name(displayName).lore(lore).build();
	}

	@Override
	public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
		if (icon.name().contains("BOOK_AND_QUILL")) {
			playSuccess(player);
			player.performCommand("tpm");
		} else if (icon.name().contains("PAPER")) {
			playSuccess(player);
			player.performCommand("tgc");
		} else if (icon.name().contains("SHEARS")) {
			playSuccess(player);
			player.performCommand("toggleclubchat");
		} else if (icon.name().contains("WATCH")) {
			if (player.hasPermission("flowercore.donator")) {
				player.performCommand("announce");
			}
			if (!player.hasPermission("flowercore.donator")) {
				playerClickSound(player);
				player.closeInventory();
				player.sendMessage(CC.translate(Lang.NO_PERM));
			}
		} else if (icon.name().contains("REDSTONE_BLOCK")) {
			playerClickSound(player);
			player.sendMessage(CC.translate("&cEmpty!"));
		} else if (icon.name().contains("DIAMOND")) {
			if (player.hasPermission("flowercore.command.announce")) {
				playSuccess(player);
				player.performCommand("donatorsettings");
			}
			if (!player.hasPermission("flowercore.command.announce")) {
				playerClickSound(player);
				player.closeInventory();
				player.sendMessage(CC.translate(Lang.NO_PERM));
			}
		} else {
			player.sendMessage(CC.translate("&7&lClicked slot #" + slot));
			playSuccess(player);
		}
	}
	//new ServersMenu().openMenu(player);
	//playNeutral(player);
}