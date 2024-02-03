package me.emmiesa.flowercore.menus.grant.button;

import lombok.AllArgsConstructor;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Lang;
import me.emmiesa.flowercore.menus.grantconfirm.GrantConfirmMenu;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.item.ItemBuilder;
import me.emmiesa.flowercore.utils.menu.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

@AllArgsConstructor
public class GrantButton extends Button {
	private final Rank rank;
	private final String playerName; // Assuming this is the name of the player who will receive the rank

	@Override
	public ItemStack getButtonItem(Player player) {
		return new ItemBuilder(rank.getIcon()).name(rank.getDisplayName()).lore(Arrays.asList(
				"&7 ",
				"&fDisplay name: &b" + rank.getDisplayName(),
				"&fPriority: &b" + rank.getPriority(),
				"&fPrefix: &b'" + rank.getPrefix() + "&b'",
				"&fSuffix: &b'" + rank.getSuffix() + "&b'",
				rank.isStaff() ? "&fStaff-Rank: &bYes" : "&fStaff-Rank: &bNo",
				rank.isDefaultRank() ? "&fDefault-Rank: &bYes" : "&fDefault-Rank: &bNo",
				" ",
				"&aClick to grant the &f" + rank.getDisplayName() + " &arank to &f" + playerName,
				"&7 ")
		).build();
	}

	@Override
	public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
		UUID playerToGrantUUID = Bukkit.getPlayerExact(playerName).getUniqueId();
		if (playerToGrantUUID != null) {
			new GrantConfirmMenu(playerToGrantUUID, rank, playerName).openMenu(player);
		} else {
			player.sendMessage(CC.translate("&cTarget player must be online to grant a rank."));
		}
	}
}
