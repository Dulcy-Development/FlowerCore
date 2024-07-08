package me.emmiesa.flowercore.rank.command.impl;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 06/05/2024 - 18:49
 */
public class RankDeleteCommand extends BaseCommand {
    @Override
    @Command(name = "rank.delete", permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /rank delete (rank-name)"));
            return;
        }

        String rankName = args[0];
        Rank rank = FlowerCore.getInstance().getRankRepository().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfigByName("messages.yml").getString("rank.deleted")
                    .replace("%rank%", FlowerCore.getInstance().getRankRepository().getRank(rankName).getColor() + rankName))
            );
            return;
        }

        FlowerCore.getInstance().getRankRepository().removeRank(rankName);
        player.sendMessage(CC.translate("&cDeleted the Rank!"));
    }
}
