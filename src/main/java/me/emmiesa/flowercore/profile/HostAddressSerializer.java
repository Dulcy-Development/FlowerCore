package me.emmiesa.flowercore.profile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 21/04/2024 - 20:47
 */
public class HostAddressSerializer {

    private static final Gson gson = new Gson();

    public static String serialize(List<String> ipList) {
        if (ipList == null || ipList.isEmpty()) {
            return "[]";
        }

        Gson gson = new Gson();
        return gson.toJson(ipList);
    }

    public static List<String> deserialize(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }
}
