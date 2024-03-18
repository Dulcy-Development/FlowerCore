package me.emmiesa.flowercore.menus.grantconfirm.button;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.nametag.NametagManager;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.item.ItemBuilder;
import me.emmiesa.flowercore.utils.menu.Button;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class GrantConfirmButton extends Button {
    private final UUID playerToGrantUUID;
    private final Rank rank;
    private final Material material;
    private final String name;
    private final List<String> lore;
    private final boolean isConfirmButton;
    private final String playerName;

    public GrantConfirmButton(String playerName, UUID playerToGrantUUID, Rank rank, Material material, String name, List<String> lore, boolean isConfirmButton) {
        this.playerToGrantUUID = playerToGrantUUID;
        this.playerName = playerName;
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

        if (clickType == ClickType.MIDDLE || clickType == ClickType.RIGHT || clickType == ClickType.NUMBER_KEY || clickType == ClickType.DROP || clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT) {
            return;
        }

        UUID playerUUID = player.getUniqueId();
        UUID playerToGrantUUID = Bukkit.getPlayerExact(playerName).getUniqueId();
        if (isConfirmButton) {
            FlowerCore.getInstance().getPlayerManager().setRank(playerToGrantUUID, rank);
            Player targetPlayer = Bukkit.getServer().getPlayer(playerToGrantUUID);
            if (targetPlayer != null) {
                player.sendMessage(CC.translate("&aYou have successfully granted " + playerName + " &athe " + rank.getDisplayName() + " &arank!"));
                targetPlayer.sendMessage(CC.translate("&aYour rank has been set to " + rank.getDisplayName() + " &aby " + FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix() + player.getDisplayName() + "&a."));
                NametagManager.updatePlayerNametag(targetPlayer);

                Button.playSuccess(player);
                player.closeInventory();

                if (FlowerCore.getInstance().getConfig("settings.yml").getBoolean("grant-settings.broadcast.enabled")) {
                    List<String> messages = FlowerCore.getInstance().getConfig("settings.yml").getStringList("grant-settings.broadcast.message");

                    String grantedBy = FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix() + player.getDisplayName();
                    String grantedPlayer = playerName;
                    String rankDisplayName = rank.getDisplayName();

                    List<String> broadcastgrant = new ArrayList<>();

                    for (String message : messages) {
                        String modifiedMessage = message
                                .replace("%granted-by%", grantedBy)
                                .replace("%granted-player%", grantedPlayer)
                                .replace("%rank%", rankDisplayName);
                        broadcastgrant.add(CC.translate(modifiedMessage));
                    }

                    for (String message : broadcastgrant) {
                        Utils.broadcastMessage(message);
                    }
                }
            }
        } else if (material.name().contains("PAPER")) {
            player.sendMessage(CC.translate("&eChoose another rank for " + playerName + "&e..."));
            player.performCommand("grant " + playerName);
        } else {
            player.closeInventory();
            player.sendMessage(CC.translate("&cThe grant for " + playerName + " &cwas canceled!"));
            Button.playNeutral(player);
        }
    }
}
