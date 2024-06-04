package me.emmiesa.flowercore.grant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 03/06/2024 - 19:44
 */
@UtilityClass
public class GrantSerializer {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Serialize a list of grants
     *
     * @param grants the list of grants
     * @return the serialized list of grants
     */
    public static List<String> serialize(List<Grant> grants) {
        if (grants == null || grants.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> serialized = new ArrayList<>();
        for (Grant grant : grants) {
            serialized.add(serializeGrant(grant));
        }
        return serialized;
    }

    /**
     * Deserialize a list of grants
     *
     * @param serialized the serialized list of grants
     * @return the deserialized list of grants
     */
    public static List<Grant> deserialize(List<String> serialized) {
        if (serialized == null || serialized.isEmpty() || serialized.get(0).isEmpty()) {
            return Collections.emptyList();
        }

        List<Grant> grants = new ArrayList<>();
        for (String string : serialized) {
            grants.add(deserializeGrant(string));
        }
        return grants;
    }

    /**
     * Serialize a grant
     *
     * @param grant the grant
     * @return the serialized grant
     */
    private static String serializeGrant(Grant grant) {
        return gson.toJson(grant);
    }

    /**
     * Deserialize a grant
     *
     * @param serialized the serialized grant
     * @return the deserialized grant
     */
    private static Grant deserializeGrant(String serialized) {
        return gson.fromJson(serialized, Grant.class);
    }
}
