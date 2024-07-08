package me.emmiesa.flowercore.rank.command.impl;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
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
public class RankSetDisplayCommand extends BaseCommand {
    @Override
    @Command(name = "rank.setdisplay", aliases = {"setrankdisplayname", "rank.setdisplayname"}, permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setdisplay (rank-name) (display-name)"));
            return;
        }

        String rankName = command.getArgs(0);
        String displayName = command.getArgs(1);

        if (FlowerCore.getInstance().getRankRepository().getRank(rankName) == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        FlowerCore.getInstance().getRankRepository().getRank(rankName).setDisplayName(displayName);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.set-display-name").replace("%rank%", rankName).replace("%display-name%", displayName)));
    }
}