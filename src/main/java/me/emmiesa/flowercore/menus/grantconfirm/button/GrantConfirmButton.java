package me.emmiesa.flowercore.menus.grantconfirm.button;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.item.ItemBuilder;
import me.emmiesa.flowercore.utils.menu.Button;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class GrantConfirmButton extends Button {
    private final UUID playerToGrantUUID;
    private final Rank rank;
    private final Material material;
    private final String name;
    private final List<String> lore;
    private final boolean isConfirmButton;

    public GrantConfirmButton(UUID playerToGrantUUID, Rank rank, Material material, String name, List<String> lore, boolean isConfirmButton) {
        this.playerToGrantUUID = playerToGrantUUID;
        this.rank = rank;
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.isConfirmButton = isConfirmButton;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(material).name(name).lore(lore).build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
        if (isConfirmButton) {
            FlowerCore.getInstance().getPlayerManager().setRank(playerToGrantUUID, rank);
            Player targetPlayer = Bukkit.getServer().getPlayer(playerToGrantUUID);
            if (targetPlayer != null) {
                targetPlayer.sendMessage(CC.translate("&aYour rank has been set to " + rank.getDisplayName() + " &aby " + player.getDisplayName()));
                Button.playSuccess(player);
                player.closeInventory();
            }
        } else {
            player.closeInventory();
            Button.playNeutral(player);
        }
    }
}
