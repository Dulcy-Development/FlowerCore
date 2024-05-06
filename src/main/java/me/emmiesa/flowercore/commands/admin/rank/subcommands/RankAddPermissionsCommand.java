package me.emmiesa.flowercore.commands.admin.rank.subcommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class RankAddPermissionsCommand extends BaseCommand {

    @Command(name = "rank.addpermissions", aliases = {"addrankperms", "rank.addperm"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank addpermissions (rank-name) (permission)"));
            return;
        }

        String rankName = args.getArgs(0);
        String permission = args.getArgs(1);

        addPermission(player, rankName, permission);
    }

    public void addPermission(Player player, String rankName, String permission) {
        Rank rank = FlowerCore.getINSTANCE().getRanksManager().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        List<String> permissions = rank.getPermissions();
        permissions.add(permission);
        rank.setPermissions(permissions);
        player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("rank.add-perm").replace("%rank%", rankName).replace("%perm%", permission)));
    }
}
