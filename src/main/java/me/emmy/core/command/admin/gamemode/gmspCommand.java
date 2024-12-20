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
public class gmspCommand extends BaseCommand {
    @Override
    @Command(name = "gmsp", aliases = {"gm.sp", "spectator", "gamemode.sp", "gamemode.3", "gamemode.spectator", "gm.3", "gm3", "gm.spectator"}, permission = "flower.command.gamemodespectator")
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

            if (target.getGameMode().equals(GameMode.SPECTATOR)) {
                args.getPlayer().sendMessage(CC.translate("&c" + target.getName() + " is already in Spectator mode."));
            } else {
                args.getPlayer().sendMessage(CC.translate("&bYou've set &r" + FlowerCore.getInstance().getProfileRepository().getRank(targetUUID).getPrefix() + target.getName() + "&b's gamemode to &3Spectator&b."));
                target.setGameMode(GameMode.SPECTATOR);
                target.sendMessage(CC.translate("&bYour gamemode has been set to &3spectator &bby &r" + FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).getPrefix() + args.getPlayer().getName() + " &b."));
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("flowercore.staff")) {
                        onlinePlayer.sendMessage(CC.translate("&7&o(" + args.getPlayer().getDisplayName() + "&7&o: Set " + target.getDisplayName() + "&7&o's gamemode spectator)")/*.replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix())))*/);
                    }
                }
            }
        } else {
            Player player = args.getPlayer();
            if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("gamemode.spectator.is_already")));
            } else {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("gamemode.spectator.switched")));
                player.setGameMode(GameMode.SPECTATOR);
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("flowercore.staff")) {
                        onlinePlayer.sendMessage(CC.translate("&7&o(" + args.getPlayer().getDisplayName() + "&7&o: Set own gamemode spectator)")/*.replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix())))*/);
                    }
                }
            }
        }
    }
}
