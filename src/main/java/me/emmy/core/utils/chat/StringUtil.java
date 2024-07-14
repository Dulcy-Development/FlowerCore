package me.emmy.core.utils.chat;

import lombok.experimental.UtilityClass;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 08/07/2024 - 23:12
 */
@UtilityClass
public class StringUtil {
    public String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}
