package me.emmiesa.flowercore.commands.admin.rank.SubCommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Lang;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.List;

public class RankAddPermissionsCommand extends BaseCommand {

    @Command(name = "rank.addpermissions", aliases = {"addrankperms"}, permission = "flowercore.staff")
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
        Rank rank = FlowerCore.instance.getRanksManager().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Lang.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        List<String> permissions = rank.getPermissions();
        permissions.add(permission);
        rank.setPermissions(permissions);
        player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("rank.addperm").replace("%rank%", rankName).replace("%perm%", permission)));
    }
}
