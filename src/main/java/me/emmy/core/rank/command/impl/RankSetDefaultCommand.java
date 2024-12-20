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
public class RankSetDefaultCommand extends BaseCommand {
    @Override
    @Command(name = "rank.setdefault", aliases = {"setrankdefault"}, permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setDefault (rank-name) (true/false)"));
            return;
        }

        String rankName = command.getArgs(0);
        boolean state = Boolean.parseBoolean(command.getArgs(1));

        setDefault(player, rankName, state);
    }

    /**
     * Sets the default rank
     *
     * @param player   the player executing the command
     * @param rankName the name of the rank
     * @param state    the state of the default rank
     */
    private void setDefault(Player player, String rankName, boolean state) {
        Rank rank = FlowerCore.getInstance().getRankRepository().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        rank.setDefaultRank(state);
        //player.sendMessage(CC.translate("&bSuccessfully set default " + (state ? "&atrue" : "&cfalse") + "&b for the " + rankName + " &brank!"));
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("rank.set-default")
                        .replace("%state%", state ? "&atrue" : "&cfalse"))
                .replace("%rank%", rankName)
        );
    }
}
