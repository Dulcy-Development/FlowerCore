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

public class gmsCommand extends BaseCommand {

    @Command(name = "gms", aliases = {"gm.s", "survival", "gamemode.s", "gamemode.0", "gamemode.survival", "gm.0", "gm0", "gm.survival"}, permission = "flowercore.staff")
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

            if (target.getGameMode().equals(GameMode.SURVIVAL)) {
                args.getPlayer().sendMessage(CC.translate("&c" + target.getName() + " is already in Survival mode."));
            } else {
                args.getPlayer().sendMessage(CC.translate("&bYou've set &r" + FlowerCore.getINSTANCE().getPlayerManager().getRank(targetUUID).getPrefix() + target.getName() + "&b's gamemode to &3Survival&b."));
                target.setGameMode(GameMode.SURVIVAL);
                target.sendMessage(CC.translate("&bYour gamemode has been set to &3survival &bby &r" + FlowerCore.getINSTANCE().getPlayerManager().getRank(playerUUID).getPrefix() + args.getPlayer().getName() + " &b."));
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("flowercore.staff")) {
                        onlinePlayer.sendMessage(CC.translate("&7&o(" + args.getPlayer().getDisplayName() + "&7&o: Set " + target.getDisplayName() + "&7&o's gamemode survival)")/*.replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix())))*/);
                    }
                }
            }
        } else {
            Player player = args.getPlayer();
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("gamemode.survival.is_already")));
            } else {
                player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("gamemode.survival.switched")));
                player.setGameMode(GameMode.SURVIVAL);
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("flowercore.staff")) {
                        onlinePlayer.sendMessage(CC.translate("&7&o(" + args.getPlayer().getDisplayName() + "&7&o: Set own gamemode survival)")/*.replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix())))*/);
                    }
                }
            }
        }
    }
}
