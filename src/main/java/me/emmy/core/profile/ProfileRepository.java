package me.emmy.core.profile;


import lombok.Getter;
import me.emmy.core.FlowerCore;
import me.emmy.core.grant.Grant;
import me.emmy.core.punishment.Punishment;
import me.emmy.core.punishment.PunishmentType;
import me.emmy.core.rank.Rank;
import me.emmy.core.tag.Tag;
import me.emmy.core.utils.Utils;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Emmy
 * @project  FlowerCore
 * @date -
 */
@Getter
public class ProfileRepository {
    private final FlowerCore plugin = FlowerCore.getInstance();
    private final Map<UUID, Profile> profiles = new ConcurrentHashMap<>();

    public void setupPlayer(UUID playerUUID) {
        getPlugin().getMongoService().initializeProfile(playerUUID);
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

    /**
     * Adds a punishment to the player's profile
     *
     * @param playerUUID player's UUID
     * @param punishment punishment to add
     */
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

    /**
     * Adds a grant to the player's profile
     *
     * @param playerUUID player's UUID
     * @param grant grant to add
     */
    public void addGrant(UUID playerUUID, Grant grant) {
        Profile profile = profiles.get(playerUUID);
        if (profile == null) {
            Bukkit.getConsoleSender().sendMessage("Profile for UUID " + playerUUID + " not found.");
            return;
        }
        List<Grant> grants = profile.getGrants();
        if (grants == null) {
            grants = new ArrayList<>();
        } else {
            grants = new ArrayList<>(grants);
        }
        grants.add(grant);
        profile.setGrants(grants);
    }

    /**
     * Deletes a grant from the player's profile
     *
     * @param playerUUID player's UUID
     * @param grant grant to delete
     */
    public void deleteGrant(UUID playerUUID, Grant grant) {
        Profile profile = profiles.get(playerUUID);
        if (profile == null) {
            Bukkit.getConsoleSender().sendMessage("Profile for UUID " + playerUUID + " not found.");
            return;
        }
        List<Grant> grants = profile.getGrants();
        if (grants != null) {
            grants.removeIf(g -> g.getRank().equals(grant.getRank()));
            profile.setGrants(grants);
        }
    }

    /**
     * Deletes a punishment from the player's profile
     *
     * @param playerUUID player's UUID
     * @param type punishment type
     * @param punishedName punished player's name
     */
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

    /**
     * [EXPERIMENTAL | NOT TESTED]
     * Deletes all punishments from the player's profile
     *
     * @param playerUUID player's UUID
     */
    public void resetPunishments(UUID playerUUID) {
        Profile profile = profiles.get(playerUUID);
        if (profile == null) {
            Utils.broadcastMessage("Profile for UUID " + playerUUID + " not found.");
            return;
        }
        List<Punishment> punishments = profile.getPunishments();
        if (punishments != null) {
            punishments.clear();
            profile.setPunishments(punishments);
        }
    }

    /**
     * Returns the player's rank
     *
     * @param playerUUID player's UUID
     * @return player's rank
     */
    public Rank getRank(UUID playerUUID) {
        if (Bukkit.getPlayer(playerUUID) != null) {
            return getProfiles().get(playerUUID).getRank();
        } else {
            Document doc = FlowerCore.getInstance().getMongoService().getCollection().find(eq("UUID", playerUUID.toString())).first();
            if (doc != null) {
                return FlowerCore.getInstance().getRankRepository().getRank(doc.getString("rank"));
            } else {
                return null;
            }
        }
    }

    /**
     * Returns the player's tag
     *
     * @param playerUUID player's UUID
     * @return player's tag
     */
    public Tag getTag(UUID playerUUID) {
        if (Bukkit.getPlayer(playerUUID) != null) {
            return getProfiles().get(playerUUID).getTag();
        } else {
            Document doc = FlowerCore.getInstance().getMongoService().getCollection().find(eq("UUID", playerUUID.toString())).first();
            if (doc != null) {
                return FlowerCore.getInstance().getTagRepository().getTag(doc.getString("tag"));
            } else {
                return null;
            }
        }
    }

    /**
     * Returns the player's rank prefix (by getting it from the config file)
     *
     * @param playerUUID player's UUID
     * @return player's rank prefix
     */
    public String getPlayerRankColor(UUID playerUUID) {
        Rank rank = getRank(playerUUID);
        if (rank != null) {
            String color = plugin.getConfig("ranks.yml").getString("ranks." + rank.getName() + ".color");
            if (color != null) {
                return color;
            }
        }
        return "&a";
    }


    /**
     * Adds default permissions to the player
     *
     * @param playerUUID player's UUID
     */
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

        Rank rank = plugin.getProfileRepository().getRank(playerUUID);
        if (rank != null && rank.getPermissions() != null) {
            for (String permission : rank.getPermissions()) {
                attachment.setPermission(permission, true);
            }
        }
    }

    /**
     * Load all profiles
     */
    public void loadAllProfiles() {
        List<OfflinePlayer> sortedPlayers = Arrays.stream(Bukkit.getOfflinePlayers())
                .sorted(Comparator.comparing(OfflinePlayer::getName))
                .collect(Collectors.toList());

        for (OfflinePlayer player : sortedPlayers) {
            FlowerCore.getInstance().getMongoService().initializeProfile(player.getUniqueId());
            Bukkit.getConsoleSender().sendMessage("Loaded profile for " + player.getName());
        }
    }
}
