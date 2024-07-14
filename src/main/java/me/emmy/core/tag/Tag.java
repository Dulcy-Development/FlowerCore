package me.emmy.core.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 30/03/2024 - 16:55
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