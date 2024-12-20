package me.emmy.core.command.admin.troll;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TrollCommand extends BaseCommand {
    @Override
    @Command(name = "troll", aliases = "playertroll", inGameOnly = false, permission = "flowercore.command.troll")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        if (args.length() == 0) {
            sender.sendMessage(CC.translate("&cUsage: /troll (player)"));
            return;
        }

        Player target = FlowerCore.getInstance().getServer().getPlayer(args.getArgs(0));

        if (target == null || !target.isOnline()) {
            sender.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("troll.not-online")).replace("%target%", args.getArgs(0)));
            return;
        }

        try {
            String path = FlowerCore.getInstance().getServer().getClass().getPackage().getName();
            String version = path.substring(path.lastIndexOf(".") + 1);

            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            Class<?> PacketPlayOutGameStateChange = Class.forName("net.minecraft.server." + version + ".PacketPlayOutGameStateChange");
            Class<?> Packet = Class.forName("net.minecraft.server." + version + ".Packet");
            Constructor<?> playOutConstructor = PacketPlayOutGameStateChange.getConstructor(int.class, float.class);
            Object packet = playOutConstructor.newInstance(5, 0);
            Object craftPlayerObject = craftPlayer.cast(target);
            Method getHandleMethod = craftPlayer.getMethod("getHandle");
            Object handle = getHandleMethod.invoke(craftPlayerObject);
            Object pc = handle.getClass().getField("playerConnection").get(handle);
            Method sendPacketMethod = pc.getClass().getMethod("sendPacket", Packet);
            sendPacketMethod.invoke(pc, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        target.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("troll.target-message")).replace("%troller%", sender.getName()));

        sender.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("troll.trolled")).replace("%player%", target.getName()));
    }
}