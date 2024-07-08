package me.emmiesa.flowercore.rank.command.impl;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class RankSetPriorityCommand extends BaseCommand {
    @Override
    @Command(name = "rank.setpriority", aliases = {"setrankpriority", "setrankprio"}, permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setPriority (rank-name) (priority)"));
            return;
        }

        String rankName = command.getArgs(0);
        int priority = Integer.parseInt(command.getArgs(1));

        setPriority(player, rankName, priority);
    }

    /**
     * Sets the priority of a rank
     *
     * @param player   the player executing the command
     * @param rankName the name of the rank
     * @param priority the priority to set
     */
    public void setPriority(Player player, String rankName, int priority) {
        Rank rank = FlowerCore.getInstance().getRankRepository().getRank(rankName);

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
