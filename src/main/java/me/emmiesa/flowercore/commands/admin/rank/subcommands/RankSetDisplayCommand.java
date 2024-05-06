package me.emmiesa.flowercore.commands.admin.rank.subcommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class RankSetDisplayCommand extends BaseCommand {

    @Command(name = "rank.setdisplay", aliases = {"setrankdisplayname", "rank.setdisplayname"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setdisplay (rank-name) (display-name)"));
            return;
        }

        String rankName = args.getArgs(0);
        String displayName = args.getArgs(1);

        if (FlowerCore.getINSTANCE().getRanksManager().getRank(rankName) == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        FlowerCore.getINSTANCE().getRanksManager().getRank(rankName).setDisplayName(displayName);
        player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("rank.set-display-name").replace("%rank%", rankName).replace("%display-name%", displayName)));
    }
}