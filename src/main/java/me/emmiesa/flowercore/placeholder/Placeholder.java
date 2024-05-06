package me.emmiesa.flowercore.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class Placeholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "flower";
    }

    @Override
    public @NotNull String getAuthor() {
        return FlowerCore.getINSTANCE().getDescription().getAuthors().toString().replace("[", "").replace("]", "");
    }

    @Override
    public @NotNull String getVersion() {
        return FlowerCore.getINSTANCE().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        UUID playerUUID = player.getUniqueId();
        switch (params) {
            case "rank":
                return FlowerCore.getINSTANCE().getPlayerManager().getRank(playerUUID).getDisplayName();
            case "rank_raw":
                return FlowerCore.getINSTANCE().getPlayerManager().getRank(playerUUID).getName();
            case "bar":
                return CC.FLOWER_BAR;
            case "is_staff":
                if (FlowerCore.getINSTANCE().getPlayerManager().getRank(playerUUID).isStaff()) {
                    return CC.translate(FlowerCore.getINSTANCE().getConfig("settings.yml").getString("placeholders.flower_is_staff.true"));
                } else {
                    return CC.translate(FlowerCore.getINSTANCE().getConfig("settings.yml").getString("placeholders.flower_is_staff.false"));
                }
            default:
                return "&fNull";
        }
    }
}