package me.emmiesa.flowercore.rank.command.impl;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.rank.Rank;
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

public class RankSetPriorityCommand extends BaseCommand {

    @Command(name = "rank.setpriority", aliases = {"setrankpriority", "setrankprio"}, permission = "flower.ranks.developer")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setPriority (rank-name) (priority)"));
            return;
        }

        String rankName = args.getArgs(0);
        int priority = Integer.parseInt(args.getArgs(1));

        setPriority(player, rankName, priority);
    }

    public void setPriority(Player player, String rankName, int priority) {
        Rank rank = FlowerCore.getInstance().getRanksManager().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        rank.setPriority(priority);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.set-priority")
                .replace("%rank%", rankName)
                .replace("%priority%", String.valueOf(priority))
        ));
    }
}
