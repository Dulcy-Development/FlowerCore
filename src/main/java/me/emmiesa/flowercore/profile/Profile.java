package me.emmiesa.flowercore.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.ranks.Rank;

import java.util.UUID;

/**
 * @author lrxh
 * @project RiftCore
 * @since 1/6/2024
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Profile {
    private UUID uuid;
    private Rank rank;
}