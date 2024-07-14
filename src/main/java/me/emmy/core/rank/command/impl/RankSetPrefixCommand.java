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
public class RankSetPrefixCommand extends BaseCommand {
    @Override
    @Command(name = "rank.setprefix", aliases = "setrankprefix", permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setPrefix (rank-name) (prefix)"));
            return;
        }

        String rankName = command.getArgs(0);
        String prefix = command.getArgs(1);

        setPrefix(player, rankName, prefix);
    }

    /**
     * Sets the prefix of a rank
     *
     * @param player  the player executing the command
     * @param rankName the name of the rank
     * @param prefix   the prefix to set
     */
    public void setPrefix(Player player, String rankName, String prefix) {
        Rank rank = FlowerCore.getInstance().getRankRepository().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        rank.setPrefix(prefix);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("rank.set-prefix")
                        .replace("%prefix%", prefix))
                .replace("%rank%", rankName)
        );
    }
}