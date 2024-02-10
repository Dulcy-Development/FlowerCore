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

public class gmaCommand extends BaseCommand {

    @Command(name = "gma", aliases = {"gm.a", "adventure", "gamemode.a", "gamemode.2", "gm.2", "gm2"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {

        if (args.length() > 0) {
            String targetName = args.getArgs(0);
            Player target = Bukkit.getServer().getPlayer(targetName);
            UUID targetUUID = target.getUniqueId();
            UUID playerUUID = args.getPlayer().getUniqueId();

            if (target == null) {
                args.getPlayer().sendMessage(CC.translate("&fNo player matching &b" + target + " &fis connected to this server"));
                return;
            }

            if (target.getGameMode().equals(GameMode.ADVENTURE)) {
                args.getPlayer().sendMessage(CC.translate("&c" + target.getName() + " is already in Adventure mode."));
            } else {
                args.getPlayer().sendMessage(CC.translate("&bYou've set &r" + FlowerCore.instance.getPlayerManager().getRank(targetUUID).getPrefix() + target.getName() + "&b's gamemode to &3Adventure&b."));
                target.setGameMode(GameMode.ADVENTURE);
                target.sendMessage(CC.translate("&bYour gamemode has been set to &3adventure &bby &r" + FlowerCore.instance.getPlayerManager().getRank(playerUUID).getPrefix() + args.getPlayer().getName() + " &b."));
            }
        } else {
            Player player = args.getPlayer();
            if (player.getGameMode().equals(GameMode.ADVENTURE)) {
                player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("gamemode.adventure.is_already")));
            } else {
                player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("gamemode.adventure.switched")));
                player.setGameMode(GameMode.ADVENTURE);
            }
        }
    }
}