package me.emmy.core.spawn.command;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TeleportSpawnCommand extends BaseCommand {
    @Override
    @Command(name = "teleportspawn", permission = "flower.command.tplobby", aliases = {"tpspawn", "tpjoinlocation", "tplobby"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getBoolean("on-join.teleport.enabled")) {
            player.teleport(FlowerCore.getInstance().getSpawnHandler().getSpawnLocation());
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("teleport.tp-spawn")));

            String sendMsg = CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("teleport.tp-spawn-action-bar"));

            sendActionBar(player, sendMsg, 5);

        } else {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("spawn.not-set.reminder.message")));
        }
    }

    private void sendActionBar(Player player, String message, int durationSeconds) {
        try {
            IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
            PacketPlayOutChat packet = new PacketPlayOutChat(chatBaseComponent, (byte) 2);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

            if (durationSeconds > 0) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        IChatBaseComponent clearChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}");
                        PacketPlayOutChat clearPacket = new PacketPlayOutChat(clearChatBaseComponent, (byte) 2);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(clearPacket);
                    }
                }.runTaskLater(FlowerCore.getInstance(), durationSeconds * 20L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}