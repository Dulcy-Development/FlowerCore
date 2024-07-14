package me.emmy.core.command.admin.troll;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TrollEverybodyCommand extends BaseCommand {
    @Override
    @Command(name = "trolleverybody", aliases = "trollall", inGameOnly = false, permission = "flowercore.command.trolleverybody")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        if (args.length() > 0) {
            sender.sendMessage(CC.translate("&cUsage: /trollall"));
        }

        try {
            String path = FlowerCore.getInstance().getServer().getClass().getPackage().getName();
            String version = path.substring(path.lastIndexOf(".") + 1);

            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            Class<?> PacketPlayOutGameStateChange = Class.forName("net.minecraft.server." + version + ".PacketPlayOutGameStateChange");
            Class<?> Packet = Class.forName("net.minecraft.server." + version + ".Packet");
            Constructor<?> playOutConstructor = PacketPlayOutGameStateChange.getConstructor(int.class, float.class);
            Object packet = playOutConstructor.newInstance(5, 0);

            for (Player target : Bukkit.getOnlinePlayers()) {
                Object craftPlayerObject = craftPlayer.cast(target);
                Method getHandleMethod = craftPlayer.getMethod("getHandle");
                Object handle = getHandleMethod.invoke(craftPlayerObject);
                Object pc = handle.getClass().getField("playerConnection").get(handle);
                Method sendPacketMethod = pc.getClass().getMethod("sendPacket", Packet);
                sendPacketMethod.invoke(pc, packet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        List<String> targetMessages = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getStringList("troll-everybody.target-message");
        for (Player target : Bukkit.getOnlinePlayers()) {
            for (String message : targetMessages) {
                target.sendMessage(CC.translate(message).replace("%troller%", sender.getName()));
                //Utils.broadcastMessage(CC.translate(message).replace("%troller%", sender.getName()));
            }
        }

        sender.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("troll-everybody.trolled")));
    }
}