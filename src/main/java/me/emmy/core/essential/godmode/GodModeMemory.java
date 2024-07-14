package me.emmy.core.essential.godmode;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 14/07/2024 - 15:06
 */
@Getter
@Setter
public class GodModeMemory {
    private Set<Player> godModePlayers = new HashSet<>();

    /**
     * Set a player to godmode
     *
     * @param player The player to set to godmode
     */
    public void enableGodMode(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);

        godModePlayers.add(player);
    }

    /**
     * Remove a player from godmode
     *
     * @param player The player to remove from godmode
     */
    public void disableGodMode(Player player) {
        player.setAllowFlight(false);
        player.setFlying(false);

        godModePlayers.remove(player);
    }

    /**
     * Check if a player is in godmode
     *
     * @param player The player to check
     * @return If the player is in godmode
     */
    public boolean isGodModeEnabled(Player player) {
        return godModePlayers.contains(player);
    }
}
