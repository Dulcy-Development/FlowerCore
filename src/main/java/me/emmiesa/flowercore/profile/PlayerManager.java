package me.emmiesa.flowercore.profile;


import lombok.Getter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.punishments.Punishment;
import me.emmiesa.flowercore.punishments.PunishmentType;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class PlayerManager {
    private final FlowerCore plugin = FlowerCore.getInstance();
    public Map<UUID, Profile> profiles = new ConcurrentHashMap<>();

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

    public void setRank(UUID playerUUID, Rank rank) {
        getProfiles().get(playerUUID).setRank(rank);
    }

    public void addPunishment(UUID playerUUID, Punishment punishment) {
        Profile profile = profiles.get(playerUUID);
        if (profile == null) {
            Utils.broadcastMessage("Profile for UUID " + playerUUID + " not found.");
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
        return getProfiles().get(playerUUID).getRank();
    }

    public void addPermissions(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) {
            return;
        }

        PermissionAttachment attachment = player.addAttachment(plugin);
        List<String> defaultPermissions = plugin.getConfig("permissions.yml").getStringList("default.permissions");
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

