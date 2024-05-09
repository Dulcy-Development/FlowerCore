package me.emmiesa.flowercore.punishments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by lrxh
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 * Recode: Remi
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Punishment {

    @SerializedName("name")
    private String name;

    @SerializedName("bannedUUID")
    private UUID bannedUUID;

    @SerializedName("by")
    private Object by;

    @SerializedName("type")
    private PunishmentType type;

    @SerializedName("reason")
    private String reason;

    @SerializedName("punishedIP")
    private String punishedIP;

    @SerializedName("ip")
    private boolean ip;

    @SerializedName("duration")
    private String duration;

    @SerializedName("active")
    private boolean active;

    public Punishment(String name, UUID bannedUUID, String by, PunishmentType type, String reason) {
        this.name = name;
        this.by = by;
        this.type = type;
        this.reason = reason;
    }

    public UUID getByUUID() {
        return by instanceof UUID ? (UUID) by : null;
    }

    public String getByString() {
        return by instanceof UUID ? ((UUID) by).toString() : (String) by;
    }
}
