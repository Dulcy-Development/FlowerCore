package me.emmiesa.flowercore.grant.menu.grants;

import lombok.AllArgsConstructor;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.grant.Grant;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.item.ItemBuilder;
import me.emmiesa.flowercore.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 03/06/2024 - 20:34
 */
@AllArgsConstructor
public class GrantsButton extends Button {

    private Grant grant;
    private final String playerName;
    private final UUID targetUUID;

    @Override
    public ItemStack getButtonItem(Player player) {
        Rank rank = FlowerCore.getInstance().getRanksManager().getRank(grant.getRank());
        if (FlowerCore.getInstance().getGrantHandler().isGrantExpired(grant)) {
            return new ItemBuilder(Material.BARRIER)
                    .name(rank.getColor() + grant.getRank())
                    .lore(
                            "&fGranted Rank: " + rank.getColor() + grant.getRank(),
                            "&fDuration: " + rank.getColor() + grant.getDuration(),
                            "&fReason: " + rank.getColor() + grant.getReason(),
                            "&fGranted at: " + rank.getColor() + grant.getAddedAt(),
                            "&fAdded by: " + rank.getColor() + grant.getAddedBy(),
                            "&fActive: " + rank.getColor() + grant.isActive(),
                            "",
                            "&fRemoved at: &c" + grant.getRemovedAt(),
                            "&fRemoved by: &c" + grant.getRemovedBy(),
                            "",
                            "&cThis grant has been removed."
                    )
                    .build();
        } else return new ItemBuilder(rank.getIcon())
                .name(rank.getColor() + grant.getRank())
                .lore(
                        "&fGranted Rank: " + rank.getColor() + grant.getRank(),
                        "&fDuration: " + rank.getColor() + grant.getDuration(),
                        "&fReason: " + rank.getColor() + grant.getReason(),
                        "&fGranted at: " + rank.getColor() + grant.getAddedAt(),
                        "&fAdded by: " + rank.getColor() + grant.getAddedBy(),
                        "&fActive: " + rank.getColor() + grant.isActive(),
                        "",
                        "&aClick to remove this grant."
                )
                .build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (clickType == ClickType.LEFT) {
            if (grant.isActive()) {
                FlowerCore.getInstance().getGrantHandler().setGrantExpired(grant, player.getName());
                player.sendMessage(CC.translate("&aYou have successfully removed the grant."));
                new GrantsMenu(playerName, targetUUID).openMenu(player);
                playFail(player);
            } else {
                player.sendMessage(CC.translate("&cThis grant has already been removed."));
            }
        }
    }
}
