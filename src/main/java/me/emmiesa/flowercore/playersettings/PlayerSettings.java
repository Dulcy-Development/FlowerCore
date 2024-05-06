package me.emmiesa.flowercore.playersettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 02/04/2024 - 10:26
 */

@Getter
@Setter
@AllArgsConstructor
public class PlayerSettings {

    private boolean globalChatEnabled;
    private boolean privateMessagesEnabled;
    private boolean messageSoundsEnabled;
}