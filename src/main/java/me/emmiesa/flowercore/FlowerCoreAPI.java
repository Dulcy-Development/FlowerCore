package me.emmiesa.flowercore;

import me.emmiesa.flowercore.profile.Profile;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.tags.Tag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 23/04/2024 - 08:30
 */

public class FlowerCoreAPI {
    static FlowerCore instance = FlowerCore.getInstance();

    public static Profile getProfile(Player player){
        if(player == null) {
            return null;
        }

        return instance.getPlayerManager().getProfileByUsername(player.getName());
    }

    public static Profile getProfileByUsername(String username){
        if (Bukkit.getPlayer(username) == null) {
            return null;
        }

        return instance.getPlayerManager().getProfileByUsername(username);
    }

    public static Profile getProfileByUUID(UUID uuid){
        if (Bukkit.getPlayer(uuid) == null) {
            return null;
        }
        return instance.getPlayerManager().getProfile(uuid);
    }

    public static Rank getRankByName(String name){
        return instance.getRanksManager().getRank(name);
    }

    public static Tag getTagByName(String name){
        return instance.getTagsManager().getTag(name);
    }

    public static boolean isDefaultRank(Rank rank){
        return rank.getName().equalsIgnoreCase(FlowerCore.getInstance().getRanksManager().getDefaultRank().getName());
    }

    public static String getPlayerDisplayName(Profile profile, UUID uuid){
        return instance.getPlayerManager().getRank(profile.getUuid()).getColor() + instance.getPlayerManager().getProfile(uuid).getUsername();
    }

    public static String getPlayerPrefix(UUID uuid) {
        return instance.getPlayerManager().getRank(uuid).getPrefix();
    }

    public static String getPlayerRank(UUID uuid) {
        return instance.getPlayerManager().getRank(uuid).getDisplayName();
    }

    public static String getPlayerRankName(UUID uuid) {
        return instance.getPlayerManager().getRank(uuid).getName();
    }

    public static String getPlayerTag(UUID uuid) {
        return instance.getPlayerManager().getTag(uuid).getDisplayName();
    }

    public static String getPlayerTagName(UUID uuid) {
        return instance.getPlayerManager().getTag(uuid).getName();
    }

}
