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
 * Date: 06/05/2024 - 18:49
 */

public class RankDeleteCommand extends BaseCommand {
    @Override
    @Command(name = "rank.delete", permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        String rankName = args.getArgs()[0];
        Rank rank = FlowerCore.getInstance().getRanksManager().getRank(rankName);

        if (args.getArgs().length < 1) {
            player.sendMessage(CC.translate("&cUsage: /rank delete (rank-name)"));
            return;
        }

        if (rank == null) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfigByName("messages.yml").getString("rank.deleted")
                    .replace("%rank%", FlowerCore.getInstance().getRanksManager().getRank(rankName).getColor() + rankName))
            );
            return;
        }

        FlowerCore.getInstance().getRanksManager().removeRank(rankName);
        player.sendMessage(CC.translate("&cDeleted the Rank!"));
    }
}
