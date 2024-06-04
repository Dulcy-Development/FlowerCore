package me.emmiesa.flowercore.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 04/06/2024 - 20:29
 */
@Getter
@Setter
public class ChatRepository {
    private final HashMap<String, Boolean> chatManager = new HashMap<>();
    private boolean isChatMuted;

    public ChatRepository(boolean isChatMuted) {
        this.isChatMuted = isChatMuted;
    }
}
