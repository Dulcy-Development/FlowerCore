package me.emmy.core.rank.command.impl;

import me.emmy.core.FlowerCore;
import me.emmy.core.locale.Locale;
import me.emmy.core.rank.Rank;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class RankSetIconCommand extends BaseCommand {
    @Override
    @Command(name = "rank.seticon", aliases = {"setrankicon"}, permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.length() < 1) {
            player.sendMessage(CC.translate("&cUsage: /rank setIcon (rank-name)"));
            return;
        }

        String rankName = command.getArgs(0);

        setMaterial(player, rankName);
    }

    /**
     * Sets the material of a rank
     *
     * @param player   the player executing the command
     * @param rankName the name of the rank
     */
    public void setMaterial(Player player, String rankName) {
        Rank rank = FlowerCore.getInstance().getRankRepository().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        if (player.getItemInHand().getType().equals(Material.AIR)) {
            player.sendMessage(CC.translate("&cYou need to be holding an item!"));
            return;
        }

        rank.setIcon(player.getItemInHand().getType());
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("rank.set-icon")
                        .replace("%rank%", rankName))
                //.replace("%icon%", player.getItemInHand().getItemMeta().getDisplayName())
        );
    }
}