package me.emmiesa.flowercore.utils;


import lombok.experimental.UtilityClass;

/**
 * @author Ryzeon
 * @project FrozedHubDeluxe
 * @since 10/11/2020 @ 13:30
 */
@UtilityClass
public class NumberUtils {

    /**
     * Check if the string is an integer
     *
     * @param s the string
     * @return if the string is an integer
     */
    public boolean checkInt(String s) {
        try {
            int i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    /**
     * Check if the string is a double
     *
     * @param s the string
     * @return if the string is a double
     */
    public boolean checkDouble(String s) {
        try {
            double i = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Check if the string is a long
     *
     * @param s the string
     * @return if the string is a long
     */
    public boolean checkLong(String s) {
        try {
            long i = Long.parseLong(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
