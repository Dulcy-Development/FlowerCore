package me.emmiesa.flowercore;

import me.emmiesa.flowercore.profile.Profile;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.tags.Tag;

import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 23/04/2024 - 08:30
 */

public class FlowerCoreAPI {
    static FlowerCore instance = FlowerCore.getInstance();

    public static Rank getRankByName(String name){
        return instance.getRanksManager().getRank(name);
    }

    public static Tag getTagByName(String name){
        return instance.getTagsManager().getTag(name);
    }

    public static String getPlayerDisplayName(Profile profile, UUID uuid){
        return instance.getPlayerManager().getRank(profile.getUuid()).getColor() + instance.getPlayerManager().getProfile(uuid).getUsername();
    }

    public static boolean isDefaultRank(Rank rank){
        return rank.getName().equalsIgnoreCase(FlowerCore.getInstance().getRanksManager().getDefaultRank().getName());
    }

    public static Profile getProfileByUUID(UUID uuid){
        return instance.getPlayerManager().getProfile(uuid);
    }

    public static Profile getProfileByUsername(String username){
        return instance.getPlayerManager().getProfileByUsername(username);
    }

}
