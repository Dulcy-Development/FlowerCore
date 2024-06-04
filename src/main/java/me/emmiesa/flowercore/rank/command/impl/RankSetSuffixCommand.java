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

public class RankSetSuffixCommand extends BaseCommand {

    @Command(name = "rank.setsuffix", aliases = {"setranksuffix"}, permission = "flower.ranks.developer")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setSuffix (rank-name) (suffix)"));
            return;
        }

        String rankName = args.getArgs(0);
        String suffix = args.getArgs(1);

        setSuffix(player, rankName, suffix);
    }

    public void setSuffix(Player player, String rankName, String suffix) {
        Rank rank = FlowerCore.getInstance().getRanksManager().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        rank.setSuffix(suffix);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.set-suffix")
                        .replace("%suffix%", suffix))
                .replace("%rank%", rankName)
        );
    }
}
