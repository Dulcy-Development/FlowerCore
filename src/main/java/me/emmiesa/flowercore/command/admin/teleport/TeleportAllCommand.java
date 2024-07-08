package me.emmiesa.flowercore.command.admin.teleport;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TeleportAllCommand extends BaseCommand {
    @Override
    @Command(name = "teleportall", permission = "flower.cmd.tpall", aliases = {"tp-all", "tpall", "tpeverybody"})
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();

        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) onlinePlayers.teleport(player.getLocation());
        Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("teleport.tp-all").replace("%player%", player.getDisplayName())));
    }
}