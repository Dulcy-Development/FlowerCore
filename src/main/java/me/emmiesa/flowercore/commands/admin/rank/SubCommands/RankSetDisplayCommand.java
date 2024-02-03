package me.emmiesa.flowercore.commands.admin.rank.SubCommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class RankSetDisplayCommand extends BaseCommand {

    @Command(name = "rank.setdisplay", aliases = "setrankdisplayname", permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setdisplay (rank-name) (display-name)"));
            return;
        }

        String rankName = args.getArgs(0);
        String displayName = args.getArgs(1);
        setDisplayName(player, rankName, displayName);
    }

    private void setDisplayName(Player player, String rankName, String displayName) {
        if (FlowerCore.instance.getRanksManager().getRank(rankName) == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        FlowerCore.instance.getRanksManager().getRank(rankName).setDisplayName(displayName);
        player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("rank.set-displayname").replace("%rank%", rankName).replace("%display-name%", displayName)));
    }
}
