package me.emmy.core.rank.command.impl;

import me.emmy.core.FlowerCore;
import me.emmy.core.locale.Locale;
import me.emmy.core.rank.Rank;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class RankAddPermissionsCommand extends BaseCommand {
    @Override
    @Command(name = "rank.addpermissions", aliases = {"addrankperms", "rank.addperm"}, permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank addpermissions (rank-name) (permission)"));
            return;
        }

        String rankName = command.getArgs(0);
        String permission = command.getArgs(1);

        addPermission(player, rankName, permission);
    }

    /**
     * Adds a permission to a rank
     *
     * @param player     the player executing the command
     * @param rankName   the name of the rank
     * @param permission the permission to add
     */
    public void addPermission(Player player, String rankName, String permission) {
        Rank rank = FlowerCore.getInstance().getRankRepository().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        List<String> permissions = rank.getPermissions();
        permissions.add(permission);
        rank.setPermissions(permissions);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("rank.add-perm").replace("%rank%", rankName).replace("%perm%", permission)));
    }
}
