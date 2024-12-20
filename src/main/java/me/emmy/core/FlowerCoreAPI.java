package me.emmy.core;

import me.emmy.core.profile.Profile;
import me.emmy.core.rank.Rank;
import me.emmy.core.tag.Tag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 23/04/2024 - 08:30
 */
public class FlowerCoreAPI {
    private static final FlowerCore INSTANCE = FlowerCore.getInstance();

    /**
     * Get the profile of a player
     *
     * @param player the player
     * @return the profile
     */
    public static Profile getProfile(Player player) {
        if (player == null) {
            return null;
        }

        return INSTANCE.getProfileRepository().getProfileByUsername(player.getName());
    }

    /**
     * Get the profile of a player by their username
     *
     * @param username the username
     * @return the profile
     */
    public static Profile getProfileByUsername(String username) {
        if (Bukkit.getPlayer(username) == null) {
            return null;
        }

        return INSTANCE.getProfileRepository().getProfileByUsername(username);
    }

    /**
     * Get the profile of a player by their UUID
     *
     * @param uuid the UUID
     * @return the profile
     */
    public static Profile getProfileByUUID(UUID uuid) {
        if (Bukkit.getPlayer(uuid) == null) {
            return null;
        }
        return INSTANCE.getProfileRepository().getProfile(uuid);
    }

    /**
     * Get a rank by its name
     *
     * @param name the name of the rank
     * @return the rank
     */
    public static Rank getRankByName(String name) {
        return INSTANCE.getRankRepository().getRank(name);
    }

    /**
     * Get a tag by its name
     *
     * @param name the name of the tag
     * @return the tag
     */
    public static Tag getTagByName(String name) {
        return INSTANCE.getTagRepository().getTag(name);
    }

    /**
     * Check if a rank is the default rank
     *
     * @param rank the rank
     * @return if the rank is the default rank
     */
    public static boolean isDefaultRank(Rank rank) {
        return rank.getName().equalsIgnoreCase(FlowerCore.getInstance().getRankRepository().getDefaultRank().getName());
    }

    /**
     * Get the display name of a player
     *
     * @param profile the profile of the player
     * @param uuid    the UUID of the player
     * @return the display name
     */
    public static String getPlayerDisplayName(Profile profile, UUID uuid) {
        return INSTANCE.getProfileRepository().getRank(profile.getUuid()).getColor() + INSTANCE.getProfileRepository().getProfile(uuid).getUsername();
    }

    /**
     * Get the prefix of a player
     *
     * @param uuid the UUID of the player
     * @return the prefix
     */
    public static String getPlayerPrefix(UUID uuid) {
        return INSTANCE.getProfileRepository().getRank(uuid).getPrefix();
    }

    /**
     * Get the rank of a player
     *
     * @param uuid the UUID of the player
     * @return the rank
     */
    public static String getPlayerRank(UUID uuid) {
        return INSTANCE.getProfileRepository().getRank(uuid).getDisplayName();
    }

    /**
     * Get the name of the rank of a player
     *
     * @param uuid the UUID of the player
     * @return the name of the rank
     */
    public static String getPlayerRankName(UUID uuid) {
        return INSTANCE.getProfileRepository().getRank(uuid).getName();
    }

    /**
     * Get the tag of a player
     *
     * @param uuid the UUID of the player
     * @return the tag
     */
    public static String getPlayerTag(UUID uuid) {
        return INSTANCE.getProfileRepository().getTag(uuid).getDisplayName();
    }

    /**
     * Get the name of the tag of a player
     *
     * @param uuid the UUID of the player
     * @return the name of the tag
     */
    public static String getPlayerTagName(UUID uuid) {
        return INSTANCE.getProfileRepository().getTag(uuid).getName();
    }
}
