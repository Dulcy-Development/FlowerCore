package me.emmiesa.flowercore.profile;


import lombok.Getter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.punishments.Punishment;
import me.emmiesa.flowercore.punishments.PunishmentType;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.tags.Tag;
import me.emmiesa.flowercore.utils.Utils;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by lrxh
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

@Getter
public class PlayerManager {
    private final FlowerCore plugin = FlowerCore.getINSTANCE();
    private final Map<UUID, Profile> profiles = new ConcurrentHashMap<>();

    public void setupPlayer(UUID playerUUID) {
        getPlugin().getMongoManager().initializeProfile(playerUUID);
        addPermissions(playerUUID);
    }

    public void addRank(Profile profile) {
        if (profile != null) {
            getProfiles().put(profile.getUuid(), profile);
        }
    }

    public Profile getProfile(UUID playerUUID) {
        return profiles.get(playerUUID);
    }

    public Profile getProfileByUsername(String username) {
        for (Profile profile : profiles.values()) {
            if (profile.getUsername().equalsIgnoreCase(username)) {
                return profile;
            }
        }
        return null;
    }

    public void setRank(UUID playerUUID, Rank rank) {
        getProfiles().get(playerUUID).setRank(rank);
    }

    public void setTag(UUID playerUUID, Tag tag) {
        getProfiles().get(playerUUID).setTag(tag);
    }

    public void resetTag(UUID playerUUID) {
        getProfiles().get(playerUUID).setTag(null);
    }

    public void addPunishment(UUID playerUUID, Punishment punishment) {
        Profile profile = profiles.get(playerUUID);
        if (profile == null) {
            Bukkit.getConsoleSender().sendMessage("Profile for UUID " + playerUUID + " not found.");
            return;
        }
        List<Punishment> punishments = profile.getPunishments();
        if (punishments == null) {
            punishments = new ArrayList<>();
        } else {
            punishments = new ArrayList<>(punishments);
        }
        punishments.add(punishment);
        profile.setPunishments(punishments);
    }

    public void removePunishment(UUID playerUUID, PunishmentType type, String punishedName) {
        Profile profile = profiles.get(playerUUID);
        if (profile == null) {
            Utils.broadcastMessage("Profile for UUID " + playerUUID + " not found.");
            return;
        }
        List<Punishment> punishments = profile.getPunishments();
        if (punishments != null) {
            punishments.removeIf(p -> p.getType() == type && p.getName().equalsIgnoreCase(punishedName));
            profile.setPunishments(punishments);
        }
    }

    public Rank getRank(UUID playerUUID) {
        if (Bukkit.getPlayer(playerUUID) != null) {
            return getProfiles().get(playerUUID).getRank();
        } else {
            Document doc = FlowerCore.getINSTANCE().getMongoManager().getCollection().find(eq("UUID", playerUUID.toString())).first();
            if (doc != null) {
                return FlowerCore.getINSTANCE().getRanksManager().getRank(doc.getString("rank"));
            } else {
                return null;
            }
        }
    }

    public Tag getTag(UUID playerUUID) {
        if (Bukkit.getPlayer(playerUUID) != null) {
            return getProfiles().get(playerUUID).getTag();
        } else {
            Document doc = FlowerCore.getINSTANCE().getMongoManager().getCollection().find(eq("UUID", playerUUID.toString())).first();
            if (doc != null) {
                return FlowerCore.getINSTANCE().getTagsManager().getTag(doc.getString("tag"));
            } else {
                return null;
            }
        }
    }

    public String getPlayerRankColor(UUID playerUUID) {
        Rank rank = getRank(playerUUID);
        if (rank != null) {
            String color = plugin.getConfig("ranks.yml").getString("rank." + rank.getName() + ".color");
            if (color != null) {
                return color;
            }
        }
        return "&a";
    }


    public void addPermissions(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) {
            return;
        }

        PermissionAttachment attachment = player.addAttachment(plugin);
        List<String> defaultPermissions = plugin.getConfig("settings.yml").getStringList("default-permissions");
        if (defaultPermissions != null) {
            for (String permission : defaultPermissions) {
                attachment.setPermission(permission, true);
            }
        }

        Rank rank = plugin.getPlayerManager().getRank(playerUUID);
        if (rank != null && rank.getPermissions() != null) {
            for (String permission : rank.getPermissions()) {
                attachment.setPermission(permission, true);
            }
        }
    }
}

