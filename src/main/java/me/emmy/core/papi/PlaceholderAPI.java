package me.emmy.core.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.emmy.core.FlowerCore;
import me.emmy.core.utils.chat.CC;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 04/02/2024 - 13:55
 */
public class PlaceholderAPI extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "flower";
    }

    @Override
    public @NotNull String getAuthor() {
        return FlowerCore.getInstance().getDescription().getAuthors().toString().replace("[", "").replace("]", "");
    }

    @Override
    public @NotNull String getVersion() {
        return FlowerCore.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        UUID playerUUID = player.getUniqueId();
        switch (params) {
            case "rank":
                return FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).getDisplayName();
            case "rank_raw":
                return FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).getName();
            case "bar":
                return CC.FLOWER_BAR;
            case "is_staff":
                if (FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).isStaff()) {
                    return CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getString("placeholders.flower_is_staff.true"));
                } else {
                    return CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getString("placeholders.flower_is_staff.false"));
                }
            default:
                return "&fNull";
        }
    }
}