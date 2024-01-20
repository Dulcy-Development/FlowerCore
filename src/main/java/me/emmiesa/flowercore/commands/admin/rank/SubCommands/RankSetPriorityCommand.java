package me.emmiesa.flowercore.commands.admin.rank.SubCommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Lang;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class RankSetPriorityCommand extends BaseCommand {

    @Command(name = "rank.setpriority", aliases = {"setrankpriority", "setrankprio"}, permission = "flowercore.staff")
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
        Rank rank = FlowerCore.instance.getRanksManager().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Lang.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        rank.setPriority(priority);
        player.sendMessage(CC.translate("&aSuccessfully set priority!"));
    }
}
