package me.emmiesa.flowercore.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.experimental.UtilityClass;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.chat.JSONMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 * @credit FrozedUHCMeetup
 */
@UtilityClass
public class Utils {

    public List<Player> getOnlinePlayers() {
        return new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
    }

    public Location tptop(Location loc) {
        return new Location(loc.getWorld(), loc.getX(), loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public void broadcastMessage(String message) {
        getOnlinePlayers().forEach(player -> player.sendMessage(CC.translate(message)));
    }
}