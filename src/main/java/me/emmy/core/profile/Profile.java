package me.emmy.core.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.emmy.core.grant.Grant;
import me.emmy.core.setting.PlayerSettings;
import me.emmy.core.punishment.Punishment;
import me.emmy.core.rank.Rank;
import me.emmy.core.tag.Tag;

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
