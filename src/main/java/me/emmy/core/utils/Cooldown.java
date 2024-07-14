package me.emmy.core.utils;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

/**
 * @author FCD
 * @project FrozedUHCMeetup
 * @since -
 */
@Getter
@Setter
public class Cooldown {

    private static DecimalFormat SECONDS_FORMAT = new DecimalFormat("#0.0");
    private long start = System.currentTimeMillis();
    private long expire;

    public Cooldown(int seconds) {
        long duration = 1000L * seconds;
        this.expire = this.start + duration;
    }

    private static String formatSeconds(long time) {
        return SECONDS_FORMAT.format(time / 1000.0F);
    }

    public long getPassed() {
        return System.currentTimeMillis() - this.start;
    }

    public long getRemaining() {
        return this.expire - System.currentTimeMillis();
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() - this.expire > 1;
    }

    public int getSecondsLeft() {
        return (int) getRemaining() / 1000;
    }

    public String getMiliSecondsLeft() {
        return formatSeconds(this.getRemaining());
    }

    public String getTimeLeft() {
        return formatTime(getSecondsLeft());
    }

    public void cancelCountdown() {
        this.expire = 0;
    }

    public static String formatTime(int timer) {
        int hours = timer / 3600;
        int secondsLeft = timer - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String formattedTime = "";

        if (hours > 0) {
            if (hours < 10)
                formattedTime += "0";
            formattedTime += hours + ":";
        }

        if (minutes < 10)
            formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10)
            formattedTime += "0";
        formattedTime += seconds;

        return formattedTime;
    }
}


