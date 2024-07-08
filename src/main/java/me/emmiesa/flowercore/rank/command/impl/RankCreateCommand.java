package me.emmiesa.flowercore.rank.command.impl;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */

public class RankCreateCommand extends BaseCommand {
    @Override
    @Command(name = "rank.create", aliases = "createrank", permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.length() > 0) {
            create(player, command.getArgs(0));
        } else {
            player.sendMessage(CC.translate("&cUsage: /rank create (rank-name)"));
        }
    }

    /**
     * Creates a rank
     *
     * @param player   the player executing the command
     * @param rankName the name of the rank
     */
    public void create(Player player, String rankName) {
        List<String> permissions = Collections.singletonList("none");
        Rank rank = new Rank(rankName, "&7" + rankName, Material.IRON_INGOT, "&7" + rankName, "&7", "&7", 0, false, false, permissions);

        FlowerCore.getInstance().getRankRepository().getRanks().add(rank);

        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.created")).replace("%rank%", rankName));

        if (FlowerCore.getInstance().getConfig("messages.yml").getBoolean("rank.save-reminder.enabled")) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.save-reminder.message")));
        }
    }
}
