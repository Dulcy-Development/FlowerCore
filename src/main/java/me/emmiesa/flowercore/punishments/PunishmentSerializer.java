package me.emmiesa.flowercore.punishments;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lrxh
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 * Recode: Remi
 */

@UtilityClass
public class PunishmentSerializer {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static List<String> serialize(List<Punishment> punishments) {
        if (punishments == null || punishments.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> serialized = new ArrayList<>();
        for (Punishment punishment : punishments) {
            serialized.add(serializePunishment(punishment));
        }
        return serialized;
    }

    public static List<Punishment> deserialize(List<String> serialized) {
        if (serialized == null || serialized.isEmpty() || serialized.get(0).isEmpty()) {
            return Collections.emptyList();
        }

        List<Punishment> punishments = new ArrayList<>();
        for (String s : serialized) {
            punishments.add(deserializePunishment(s));
        }
        return punishments;
    }

    private static String serializePunishment(Punishment punishment) {
        return gson.toJson(punishment);
    }

    private static Punishment deserializePunishment(String serialized) {
        return gson.fromJson(serialized, Punishment.class);
    }
}
