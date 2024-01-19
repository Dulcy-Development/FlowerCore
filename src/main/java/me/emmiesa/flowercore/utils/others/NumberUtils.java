package me.emmiesa.flowercore.utils.others;

/*
 *
 * Taken from frozedclub on github
 *
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */

public class NumberUtils {

    public static boolean checkInt(String s) {
        try {
            int i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean checkDouble(String s) {
        try {
            double i = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean checkLong(String s) {
        try {
            long i = Long.parseLong(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
