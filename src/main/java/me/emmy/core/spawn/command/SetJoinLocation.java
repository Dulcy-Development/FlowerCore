package me.emmy.core.spawn.command;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class SetJoinLocation extends BaseCommand {
    @Override
    @Command(name = "setjoinlocation", permission = "flower.command.setjoinlocation", aliases = "setspawnlocation")
    public void onCommand(CommandArgs command) {
        Player player = (Player) command.getSender();
        Location loc = player.getLocation();
        FlowerCore.getInstance().getSpawnHandler().setSpawnLocation(loc);

        String message = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("spawn-set");
        message = message.replace("{world}", loc.getWorld().getName())
                .replace("{x}", String.format("%.2f", loc.getX()))
                .replace("{y}", String.format("%.2f", loc.getY()))
                .replace("{z}", String.format("%.2f", loc.getZ()))
                .replace("{yaw}", String.format("%.2f", loc.getYaw()))
                .replace("{pitch}", String.format("%.2f", loc.getPitch()));

        if (FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getBoolean("spawn-set-action-bar.enabled")) {
            String sendMsg = CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("spawn-set-action-bar.message"));

            int duration = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getInt("spawn-set-action-bar.duration");

            sendActionBar(player, sendMsg, duration);
            player.sendMessage(CC.translate(message));
        }
    }

    private void sendActionBar(Player player, String message, int durationSeconds) {
        try {
            new BukkitRunnable() {
                int ticksLeft = durationSeconds * 20;

                @Override
                public void run() {
                    if (ticksLeft <= 0) {
                        this.cancel();
                    } else {
                        IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
                        PacketPlayOutChat packet = new PacketPlayOutChat(chatBaseComponent, (byte) 2);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                    }
                    ticksLeft -= 2;
                }
            }.runTaskTimer(FlowerCore.getInstance(), 0L, 2L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}