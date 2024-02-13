package me.emmiesa.flowercore.punishments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Punishment {

    private UUID uuid;
    private UUID by;
    private PunishmentType type;
    private String reason;
    private String punishedIP;
    private boolean ip;

    // public boolean isPermanent() {
    //     return this.duration.equalsIgnoreCase("Permanent") || this.expiresOn.equalsIgnoreCase("Never");
    //}
}
