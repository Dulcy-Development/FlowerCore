package me.emmiesa.flowercore.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ProfilePlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "flower";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Emmiesa";
    }

    @Override
    public @NotNull String getVersion() {
        return FlowerCore.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        UUID playerUUID = player.getUniqueId();
        if (params.equalsIgnoreCase("player_rank")) {
            FlowerCore.getInstance().getPlayerManager().getRank(playerUUID);
        } else if (params.equalsIgnoreCase("isstaff")) {
            if (FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).isStaff()) {
                return CC.translate("&aTrue");
            } else {
                return CC.translate("&cFalse");
            }
        }
        return "&fNull";
    }
}