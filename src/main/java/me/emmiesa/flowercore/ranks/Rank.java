package me.emmiesa.flowercore.ranks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

/**
 * @author lrxh
 * @project RiftCore
 * @since 1/5/2024
 */

@Getter
@Setter
@AllArgsConstructor
public class Rank {
    private String name;
    private String displayName;
    private Material icon;
    private String prefix;
    private String suffix;
    private int priority;
    private boolean defaultRank;
    private boolean staff;
    private List<String> permissions;
}
