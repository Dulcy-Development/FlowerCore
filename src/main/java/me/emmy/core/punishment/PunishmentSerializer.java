package me.emmy.core.punishment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
@UtilityClass
public class PunishmentSerializer {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Serialize a list of punishments
     *
     * @param punishments List of punishments
     * @return Serialized list of punishments
     */
    public List<String> serialize(List<Punishment> punishments) {
        if (punishments == null || punishments.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> serialized = new ArrayList<>();
        for (Punishment punishment : punishments) {
            serialized.add(serializePunishment(punishment));
        }
        return serialized;
    }

    /**
     * Deserialize a list of punishments
     *
     * @param serialized Serialized list of punishments
     * @return List of punishments
     */
    public List<Punishment> deserialize(List<String> serialized) {
        if (serialized == null || serialized.isEmpty() || serialized.get(0).isEmpty()) {
            return Collections.emptyList();
        }

        List<Punishment> punishments = new ArrayList<>();
        for (String s : serialized) {
            punishments.add(deserializePunishment(s));
        }
        return punishments;
    }

    /**
     * Serialize a punishment
     *
     * @param punishment Punishment
     * @return Serialized punishment
     */
    private String serializePunishment(Punishment punishment) {
        return gson.toJson(punishment);
    }

    /**
     * Deserialize a punishment
     *
     * @param serialized Serialized punishment
     * @return Punishment
     */
    private Punishment deserializePunishment(String serialized) {
        return gson.fromJson(serialized, Punishment.class);
    }
}
