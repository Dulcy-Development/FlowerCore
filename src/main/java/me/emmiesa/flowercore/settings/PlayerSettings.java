package me.emmiesa.flowercore.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 02/04/2024 - 10:26
 */
@Getter
@Setter
@AllArgsConstructor
public class PlayerSettings {
    private boolean globalChatEnabled;
    private boolean privateMessagesEnabled;
    private boolean messageSoundsEnabled;
}