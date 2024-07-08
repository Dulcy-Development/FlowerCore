package me.emmiesa.flowercore.rank.command.impl;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
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
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.add-perm").replace("%rank%", rankName).replace("%perm%", permission)));
    }
}
