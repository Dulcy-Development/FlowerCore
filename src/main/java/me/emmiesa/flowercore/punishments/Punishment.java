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

    @SerializedName("uuid")
    private UUID uuid;

    @SerializedName("name")
    private String name;

    @SerializedName("by")
    private UUID by;

    @SerializedName("type")
    private PunishmentType type;

    @SerializedName("reason")
    private String reason;

    @SerializedName("punishedIP")
    private String punishedIP;

    @SerializedName("ip")
    private boolean ip;
}
