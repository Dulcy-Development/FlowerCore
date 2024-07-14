package me.emmy.core.command.admin.teleport;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.Utils;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
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
        Utils.broadcastMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("teleport.tp-all").replace("%player%", player.getDisplayName())));
    }
}