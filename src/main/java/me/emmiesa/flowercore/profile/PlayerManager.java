package me.emmiesa.flowercore.profile;


import lombok.Getter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

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
        getProfiles().put(profile.getUuid(), profile);
    }

    public void setRank(UUID playerUUID, Rank rank) {
        getProfiles().get(playerUUID).setRank(rank);
    }

    public Rank getRank(UUID playerUUID) {
        return getProfiles().get(playerUUID).getRank();
    }

    public void addPermissions(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);

        PermissionAttachment attachment = player.addAttachment(plugin);

        for (String permission : plugin.getConfig("permissions.yml").getStringList("default.permissions")) {
            attachment.setPermission(permission, true);
        }

        Rank rank = plugin.getPlayerManager().getRank(playerUUID);

        for (String permission : rank.getPermissions()) {
            attachment.setPermission(permission, true);
        }
        /*CC.sendError(attachment.getPermissions().toString());*/
    }
}

