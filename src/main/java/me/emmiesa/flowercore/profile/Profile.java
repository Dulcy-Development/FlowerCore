package me.emmiesa.flowercore.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.grant.Grant;
import me.emmiesa.flowercore.settings.PlayerSettings;
import me.emmiesa.flowercore.punishment.Punishment;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.tag.Tag;

import java.util.List;
import java.util.UUID;

/**
 * @author Emmy
 * @project  FlowerCore
 * @date -
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Profile {
    private String username;
    private UUID uuid;
    private Rank rank;
    private Tag tag;
    private List<Grant> grants;
    private List<Punishment> punishments;
    private PlayerSettings playerSettings;
}
