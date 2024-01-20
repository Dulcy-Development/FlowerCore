package me.emmiesa.flowercore.utils.chat;

public class StringUtil {
    public static String repeat(char character, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(character);
        }
        return result.toString();
    }

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
//a
}
