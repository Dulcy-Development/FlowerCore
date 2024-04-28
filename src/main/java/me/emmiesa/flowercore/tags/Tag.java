package me.emmiesa.flowercore.tags;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 30/03/2024 - 16:55
 */

@Getter
@Setter
@AllArgsConstructor
public class Tag {
    private String name;
    private String displayName;
    private Material icon;
    private String color;
}