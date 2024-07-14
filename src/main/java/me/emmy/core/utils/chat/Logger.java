package me.emmy.core.utils.chat;

import lombok.experimental.UtilityClass;
import me.emmy.core.FlowerCore;
import org.bukkit.Bukkit;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 14/07/2024 - 14:43
 */
@UtilityClass
public class Logger {

    /**
     * Log a task's time to the console
     *
     * @param taskName Task name
     * @param runnable Task to run
     */
    public void logTime(String taskName, Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getPrefix() + "&fSuccessfully loaded &d" + taskName + " &fin &d" + (end - start) + "ms&f."));
    }
}
