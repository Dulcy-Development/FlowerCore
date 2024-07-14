package me.emmy.core.rank.command.impl;

import me.emmy.core.FlowerCore;
import me.emmy.core.rank.menu.RankListMenu;
import me.emmy.core.rank.Rank;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.utils.chat.StringUtil;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class RankListCommand extends BaseCommand {
    @Override
    @Command(name = "rank.list", aliases = {"ranklist", "listranks"}, permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getBoolean("rank-settings.list.send-as-message")) {
            sendAsMessage(player);
        } else if (FlowerCore.getInstance().getConfigHandler().getConfig("settings.yml").getBoolean("rank-settings.list.open-menu")) {
            openListMenu(player);
        } else {
            openListMenu(player);
        }
    }

    /**
     * Opens the rank list menu
     *
     * @param player the player executing the command
     */
    private void openListMenu(Player player) {
        new RankListMenu().openMenu(player);
    }

    /**
     * Sends the rank list as a message
     *
     * @param player the player executing the command
     */
    private void sendAsMessage(Player player) {
        List<Rank> ranks = FlowerCore.getInstance().getRankRepository().getRanks();

        int maxLength = ranks.stream()
                .mapToInt(rank -> ChatColor.stripColor(rank.getDisplayName()).length())
                .max()
                .orElse(0);

        player.sendMessage(" ");
        player.sendMessage(CC.FLOWER_BAR);
        player.sendMessage(CC.translate("   &b&lRank list &f(" + FlowerCore.getInstance().getConfigHandler().getConfig("ranks.yml").getConfigurationSection("ranks").getKeys(false).size()) + CC.translate("&f)"));

        for (Rank rank : ranks) {
            String paddedName = StringUtil.padRight(rank.getColor() + rank.getName() + " &8&l┃&r " + rank.getPrefix(), maxLength);
            String rankLine = "    &f► " + paddedName;
            player.sendMessage(CC.translate(rankLine));
        }

        player.sendMessage(CC.FLOWER_BAR);
        player.sendMessage(" ");
    }
}
