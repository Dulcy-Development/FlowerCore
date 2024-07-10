package me.emmiesa.flowercore.punishment;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @authors Emmy, Remi
 * @project FlowerCore
 * @date 10/07/2024 - 21:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Punishment {

    @SerializedName("name")
    private String name;

    @SerializedName("UUID")
    private UUID UUID;

    @SerializedName("by")
    private String by;

    @SerializedName("type")
    private PunishmentType type;

    @SerializedName("reason")
    private String reason;

    @SerializedName("ipAddress")
    private String ipAddress;

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
     * @param UUID UUID of the banned player
     * @param by       Punishment issuer
     * @param type     Punishment type
     * @param reason   Punishment reason
     */
    public Punishment(String name, UUID UUID, String by, PunishmentType type, String reason) {
        this.name = name;
        this.by = by;
        this.type = type;
        this.reason = reason;
    }


}
