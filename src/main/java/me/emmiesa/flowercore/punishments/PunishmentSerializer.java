package me.emmiesa.flowercore.punishments;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class PunishmentSerializer {

    public static List<String> serialize(List<Punishment> punishments) {
        if (punishments == null) {
            return Collections.singletonList("null");
        }
        List<String> serialized = new ArrayList<>();
        for (Punishment punishment : punishments) {
            serialized.add(punishment.getUuid()
                    + ":" + punishment.getType().toString()
                    + ":" + punishment.getBy().toString()
                    + ":" + punishment.getReason()
                    + ":" + punishment.getPunishedIP()
                    + ":" + punishment.isIp());
        }
        // uuid:type:by:reason:punishedIP:isIP

        return serialized;
    }

    public static List<Punishment> deserialize(List<String> serialized) {
        if (serialized == null || serialized.isEmpty() || serialized.get(0).equals("null")) {
            return null;
        }

        List<Punishment> punishments = new ArrayList<>();
        for (String s : serialized) {
            String[] parts = s.split(":");
            if (parts.length == 8) {
                UUID uuid = UUID.fromString(parts[0]);
                PunishmentType type = PunishmentType.valueOf(parts[1]);
                UUID by = UUID.fromString(parts[2]);
                String reason = parts[3];
                String punishedIP = parts[4];
                boolean ip = Boolean.parseBoolean(parts[5]);

                Punishment punishment = new Punishment(uuid, by, type, reason, punishedIP, ip);
                punishments.add(punishment);
            }
        }

        return punishments;
    }

}
