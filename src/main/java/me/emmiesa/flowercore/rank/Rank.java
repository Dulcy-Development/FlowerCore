package me.emmiesa.flowercore.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

@Getter
@Setter
@AllArgsConstructor
public class Rank {
    private String name;

    private String displayName;
    private Material icon;
    private String prefix;
    private String color;
    private String suffix;
    private int priority;
    private boolean defaultRank;
    private boolean staff;
    private List<String> permissions;
}