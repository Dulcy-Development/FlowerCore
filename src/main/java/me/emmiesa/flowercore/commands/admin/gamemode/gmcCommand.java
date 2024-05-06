package me.emmiesa.flowercore.commands.admin.gamemode;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class gmcCommand extends BaseCommand {

    @Command(name = "gmc", aliases = {"gm.c", "creative", "gamemode.c", "gamemode.1", "gamemode.creative", "gm.1", "gm1", "gm.creative"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        if (args.length() > 0) {
            String targetName = args.getArgs(0);
            Player target = Bukkit.getServer().getPlayer(targetName);

            if (target == null) {
                args.getPlayer().sendMessage(CC.translate("&fNo player matching &b" + targetName + " &fis connected to this server"));
                return;
            }

            UUID targetUUID = target.getUniqueId();
            UUID playerUUID = args.getPlayer().getUniqueId();

            if (target.getGameMode().equals(GameMode.CREATIVE)) {
                args.getPlayer().sendMessage(CC.translate("&c" + target.getName() + " is already in Creative mode."));
            } else {
                args.getPlayer().sendMessage(CC.translate("&bYou've set &r" + FlowerCore.getINSTANCE().getPlayerManager().getRank(targetUUID).getPrefix() + target.getName() + "&b's gamemode to &3creative&b."));
                target.setGameMode(GameMode.CREATIVE);
                target.sendMessage(CC.translate("&bYour gamemode has been set to &3creative &bby &r" + FlowerCore.getINSTANCE().getPlayerManager().getRank(playerUUID).getPrefix() + args.getPlayer().getName() + " &b."));
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("flowercore.staff")) {
                        onlinePlayer.sendMessage(CC.translate("&7&o(" + args.getPlayer().getDisplayName() + "&7&o: Set " + target.getDisplayName() + "&7&o's gamemode creative)")/*.replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix())))*/);
                    }
                }
            }
        } else {
            Player player = args.getPlayer();
            if (player.getGameMode().equals(GameMode.CREATIVE)) {
                player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("gamemode.creative.is_already")));
            } else {
                player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("gamemode.creative.switched")));
                player.setGameMode(GameMode.CREATIVE);
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("flowercore.staff")) {
                        onlinePlayer.sendMessage(CC.translate("&7&o(" + args.getPlayer().getDisplayName() + "&7&o: Set own gamemode creative)")/*.replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix())))*/);
                    }
                }
            }
        }
    }
}