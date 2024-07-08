package me.emmiesa.flowercore.rank.command.impl;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class RankSetStaffCommand extends BaseCommand {
    @Override
    @Command(name = "rank.setstaff", aliases = {"setrankstaff"}, permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setStaff (rank-name) (true/false)"));
            return;
        }

        String rankName = command.getArgs(0);
        boolean state = Boolean.parseBoolean(command.getArgs(1));

        setStaff(player, rankName, state);
    }

    /**
     * Sets the staff status of a rank
     *
     * @param player   the player executing the command
     * @param rankName the name of the rank
     * @param state    the state of the staff status
     */
    public void setStaff(Player player, String rankName, boolean state) {
        Rank rank = FlowerCore.getInstance().getRankRepository().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        rank.setStaff(state);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.set-staff")
                        .replace("%state%", state ? "&atrue" : "&cfalse"))
                .replace("%rank%", rankName)
        );
    }
}
