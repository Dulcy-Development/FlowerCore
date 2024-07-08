package me.emmiesa.flowercore.punishment;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 * @recode Remi
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

    /**
     * Constructor for a punishment
     *
     * @param name     Name of the punishment
     * @param bannedUUID UUID of the banned player
     * @param by       Punishment issuer
     * @param type     Punishment type
     * @param reason   Punishment reason
     */
    public Punishment(String name, UUID bannedUUID, String by, PunishmentType type, String reason) {
        this.name = name;
        this.by = by;
        this.type = type;
        this.reason = reason;
    }

    /**
     * Get the UUID of the punishment issuer
     *
     * @return UUID of the punishment issuer
     */
    public UUID getByUUID() {
        return by instanceof UUID ? (UUID) by : null;
    }

    /**
     * Get the string of the punishment issuer
     *
     * @return String of the punishment issuer
     */
    public String getByString() {
        return by instanceof UUID ? ((UUID) by).toString() : (String) by;
    }
}
