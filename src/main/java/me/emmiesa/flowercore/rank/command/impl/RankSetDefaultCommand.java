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

public class RankSetDefaultCommand extends BaseCommand {

    @Command(name = "rank.setdefault", aliases = {"setrankdefault"}, permission = "flower.ranks.developer")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setDefault (rank-name) (true/false)"));
            return;
        }

        String rankName = args.getArgs(0);
        boolean state = Boolean.parseBoolean(args.getArgs(1));

        setDefault(player, rankName, state);
    }

    public void setDefault(Player player, String rankName, boolean state) {
        Rank rank = FlowerCore.getInstance().getRanksManager().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        rank.setDefaultRank(state);
        //player.sendMessage(CC.translate("&bSuccessfully set default " + (state ? "&atrue" : "&cfalse") + "&b for the " + rankName + " &brank!"));
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.set-default")
                        .replace("%state%", state ? "&atrue" : "&cfalse"))
                .replace("%rank%", rankName)
        );
    }
}