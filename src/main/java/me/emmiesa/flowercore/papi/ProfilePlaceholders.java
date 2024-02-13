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
        if (params.equalsIgnoreCase("rank")) {
            return FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getDisplayName();
        } else if (params.equalsIgnoreCase("rank_raw")) {
            return FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getName();
        } else if (params.equalsIgnoreCase("isstaffrank")) {
            if (FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).isStaff()) {
                return CC.translate(FlowerCore.getInstance().getConfig("placeholders.yml").getString("flower_player_rank_isstaff.true"));
            } else {
                return CC.translate(FlowerCore.getInstance().getConfig("placeholders.yml").getString("flower_player_rank_isstaff.false"));
            }
        }
        return "&fNull";
    }
}