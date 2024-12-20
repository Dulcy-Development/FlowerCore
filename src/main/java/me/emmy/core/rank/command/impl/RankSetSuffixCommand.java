package me.emmy.core.rank.command.impl;

import me.emmy.core.FlowerCore;
import me.emmy.core.locale.Locale;
import me.emmy.core.rank.Rank;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class RankSetSuffixCommand extends BaseCommand {
    @Override
    @Command(name = "rank.setsuffix", aliases = {"setranksuffix"}, permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setSuffix (rank-name) (suffix)"));
            return;
        }

        String rankName = command.getArgs(0);
        String suffix = command.getArgs(1);

        setSuffix(player, rankName, suffix);
    }

    /**
     * Sets the suffix of a rank
     *
     * @param player  the player executing the command
     * @param rankName the name of the rank
     * @param suffix   the suffix to set
     */
    public void setSuffix(Player player, String rankName, String suffix) {
        Rank rank = FlowerCore.getInstance().getRankRepository().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        rank.setSuffix(suffix);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("rank.set-suffix")
                        .replace("%suffix%", suffix))
                .replace("%rank%", rankName)
        );
    }
}
