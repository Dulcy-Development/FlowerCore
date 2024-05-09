package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class FeedCommand extends BaseCommand {

    @Command(name = "feed", aliases = "sethungerfull", permission = "flower.command.feed")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() == 0) {
            healPlayer(player);
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("feed.fed")));
        } else if (args.length() == 1) {
            Player targetPlayer = player.getServer().getPlayer(args.getArgs()[0]); //Bukkit.getPlayer(command.getArgs()[0]);
            if (targetPlayer != null && targetPlayer.isOnline()) {
                healPlayer(targetPlayer);
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("feed.target-fed").replace("%target%", targetPlayer.getName())));
            } else {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("feed.target-not-found").replace("%target%", args.getArgs()[0])));
            }
        } else {
            player.sendMessage(CC.translate("&cUsage: /feed (player)"));
        }
    }

    private void healPlayer(Player player) {
        player.setFoodLevel(20);
    }
}