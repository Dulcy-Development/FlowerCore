package me.emmiesa.flowercore.commands.admin.spawn;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportSpawnCommand extends BaseCommand {

    @Command(name = "teleportspawn", permission = "flowercore.staff", aliases = {"tpspawn", "tpjoinlocation", "tplobby"})
    @Override
    public void onCommand(CommandArgs args) {
        final Player player = args.getPlayer();

        if (FlowerCore.getInstance().getConfig("settings.yml").getBoolean("on-join.teleport.enabled")) {
            player.teleport(FlowerCore.getInstance().getSpawnLocation());
            //player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("teleport.tp-spawn")));

            String sendmsg = CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("teleport.tp-spawn-action-bar"));

            sendActionBar(player, sendmsg, 5);

        } else {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("spawn.not-set.reminder.message")));
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