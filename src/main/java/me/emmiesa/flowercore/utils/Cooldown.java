package me.emmiesa.flowercore.utils;

import lombok.Getter;
import lombok.Setter;
import me.emmiesa.flowercore.utils.Utils;

import java.text.DecimalFormat;

/**
 * FrozedUHCMeetup
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
        return Utils.formatTime(getSecondsLeft());
    }

    public void cancelCountdown() {
        this.expire = 0;
    }
}


