package me.emmiesa.flowercore.commands.admin.rank.SubCommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class RankSetPrefixCommand extends BaseCommand {

    @Command(name = "rank.setprefix", aliases = "setrankprefix", permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setPrefix (rank-name) (prefix)"));
            return;
        }

        String rankName = args.getArgs(0);
        String prefix = args.getArgs(1);

        setPrefix(player, rankName, prefix);
    }

    public void setPrefix(Player player, String rankName, String prefix) {
        Rank rank = FlowerCore.instance.getRanksManager().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate("&4ERROR &8| &cRank not found!"));
            return;
        }

        rank.setPrefix(prefix);
        player.sendMessage(CC.translate("&aSuccessfully set prefix!"));
    }
}