package me.emmiesa.flowercore.commands.admin.rank.subcommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.ranks.Rank;
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

public class RankSetStaffCommand extends BaseCommand {

    @Command(name = "rank.setstaff", aliases = {"setrankstaff"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setStaff (rank-name) (true/false)"));
            return;
        }

        String rankName = args.getArgs(0);
        boolean state = Boolean.parseBoolean(args.getArgs(1));

        setStaff(player, rankName, state);
    }

    public void setStaff(Player player, String rankName, boolean state) {
        Rank rank = FlowerCore.getInstance().getRanksManager().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        rank.setStaff(state);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.set-staff")
                        .replace("%state%", state ? "&atrue" : "&cfalse"))
                .replace("%rank%", rankName)
        );
    }
}
