package me.emmiesa.flowercore.rank.command.impl;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class RankSetColorCommand extends BaseCommand {
    @Override
    @Command(name = "rank.setcolor", aliases = "setrankcolor", permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setcolor (rank-name) (color-code)"));
            return;
        }

        String rankName = command.getArgs(0);
        String color = command.getArgs(1);
        setColor(player, rankName, color);
    }

    /**
     * Sets the color of a rank
     *
     * @param player   the player executing the command
     * @param rankName the name of the rank
     * @param color    the color to set
     */
    private void setColor(Player player, String rankName, String color) {
        if (FlowerCore.getInstance().getRankRepository().getRank(rankName) == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        FlowerCore.getInstance().getRankRepository().getRank(rankName).setColor(color);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.set-color").replace("%rank%", rankName).replace("%color%", color)));
    }
}
