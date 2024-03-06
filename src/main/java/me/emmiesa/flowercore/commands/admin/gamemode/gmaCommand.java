package me.emmiesa.flowercore.commands.admin.gamemode;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import me.emmiesa.flowercore.utils.command.Completer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class gmaCommand extends BaseCommand {

    @Completer(name = "gamemode", aliases = "gm")
    public List<String> gmCompleter(CommandArgs args) {
        List<String> commands = new ArrayList<>();
        if (args.length() == 1) {
            commands.add("creative");
            commands.add("survival");
            commands.add("adventure");
            commands.add("spectator");
        }
        return commands;
    }

    @Command(name = "gma", aliases = {"gm.a", "adventure", "gamemode.a", "gamemode.2", "gamemode.adventure", "gm.2", "gm2", "gm.adventure"}, permission = "flowercore.staff")
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

            if (target.getGameMode().equals(GameMode.ADVENTURE)) {
                args.getPlayer().sendMessage(CC.translate("&c" + target.getName() + " is already in Adventure mode."));
            } else {
                args.getPlayer().sendMessage(CC.translate("&bYou've set &r" + FlowerCore.getInstance().getPlayerManager().getRank(targetUUID).getPrefix() + target.getName() + "&b's gamemode to &3Adventure&b."));
                target.setGameMode(GameMode.ADVENTURE);
                target.sendMessage(CC.translate("&bYour gamemode has been set to &3adventure &bby &r" + FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix() + args.getPlayer().getName() + " &b."));
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("flowercore.staff")) {
                        onlinePlayer.sendMessage(CC.translate("&7&o(" + args.getPlayer().getDisplayName() + "&7&o: Set " + target.getDisplayName() + "&7&o's gamemode adventure)")/*.replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix())))*/);
                    }
                }
            }
        } else {
            Player player = args.getPlayer();
            if (player.getGameMode().equals(GameMode.ADVENTURE)) {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("gamemode.adventure.is_already")));
            } else {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("gamemode.adventure.switched")));
                player.setGameMode(GameMode.ADVENTURE);
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("flowercore.staff")) {
                        onlinePlayer.sendMessage(CC.translate("&7&o(" + args.getPlayer().getDisplayName() + "&7&o: Set own gamemode adventure)")/*.replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix())))*/);
                    }
                }
            }
        }
    }
}
