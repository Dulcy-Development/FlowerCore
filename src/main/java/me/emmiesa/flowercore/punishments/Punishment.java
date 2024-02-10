package me.emmiesa.flowercore.punishments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/*
 *
 * Project: Experimental Core
 *
 */

@RequiredArgsConstructor
@Getter @Setter
public class Punishment {

    private String id;
    private UUID punishedUUID;
    private String punishedby;
    private PunishmentType punishtype;
    private String reason, madeOn, expiresOn, duration, punishedIP;
    private boolean ip, silent, active;

    public boolean isPermanent() {
        return this.duration.equalsIgnoreCase("Permanent") || this.expiresOn.equalsIgnoreCase("Never");
    }

}
