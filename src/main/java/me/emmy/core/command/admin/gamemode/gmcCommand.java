package me.emmy.core.command.admin.gamemode;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class gmcCommand extends BaseCommand {
    @Override
    @Command(name = "gmc", aliases = {"gm.c", "creative", "gamemode.c", "gamemode.1", "gamemode.creative", "gm.1", "gm1", "gm.creative"}, permission = "flower.command.gamemodecreative")
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
                args.getPlayer().sendMessage(CC.translate("&bYou've set &r" + FlowerCore.getInstance().getProfileRepository().getRank(targetUUID).getPrefix() + target.getName() + "&b's gamemode to &3creative&b."));
                target.setGameMode(GameMode.CREATIVE);
                target.sendMessage(CC.translate("&bYour gamemode has been set to &3creative &bby &r" + FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).getPrefix() + args.getPlayer().getName() + " &b."));
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("flowercore.staff")) {
                        onlinePlayer.sendMessage(CC.translate("&7&o(" + args.getPlayer().getDisplayName() + "&7&o: Set " + target.getDisplayName() + "&7&o's gamemode creative)")/*.replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix())))*/);
                    }
                }
            }
        } else {
            Player player = args.getPlayer();
            if (player.getGameMode().equals(GameMode.CREATIVE)) {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("gamemode.creative.is_already")));
            } else {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("gamemode.creative.switched")));
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