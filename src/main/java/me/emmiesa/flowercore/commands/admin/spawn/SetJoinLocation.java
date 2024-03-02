package me.emmiesa.flowercore.commands.admin.spawn;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SetJoinLocation extends BaseCommand {

    @Command(name = "setjoinlocation", permission = "flowercore.command.tphere", aliases = "setspawnlocation")
    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = (Player) cmd.getSender();
        Location loc = player.getLocation();
        FlowerCore.getInstance().setSpawnLocation(loc);

        String message = FlowerCore.getInstance().getConfig("messages.yml").getString("spawn-set");
        message = message.replace("{world}", loc.getWorld().getName())
                .replace("{x}", String.format("%.2f", loc.getX()))
                .replace("{y}", String.format("%.2f", loc.getY()))
                .replace("{z}", String.format("%.2f", loc.getZ()))
                .replace("{yaw}", String.format("%.2f", loc.getYaw()))
                .replace("{pitch}", String.format("%.2f", loc.getPitch()));

        if (FlowerCore.getInstance().getConfig("messages.yml").getBoolean("spawn-set-action-bar.enabled")) {
            String sendMsg = CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("spawn-set-action-bar.message"));

            int duration = FlowerCore.getInstance().getConfig("messages.yml").getInt("spawn-set-action-bar.duration");

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