package me.emmiesa.flowercore.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.playersettings.PlayerSettings;
import me.emmiesa.flowercore.punishments.Punishment;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.tags.Tag;

import java.util.List;
import java.util.UUID;

/**
 * Created by lrxh
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Profile {
    private UUID uuid;
    private String username;
    private Rank rank;
    private Tag tag;
    private List<Punishment> punishments;
    private PlayerSettings playerSettings;
}
