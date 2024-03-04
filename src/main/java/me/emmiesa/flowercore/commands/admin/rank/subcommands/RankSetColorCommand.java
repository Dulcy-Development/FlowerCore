package me.emmiesa.flowercore.commands.admin.rank.subcommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class RankSetColorCommand extends BaseCommand {

    @Command(name = "rank.setcolor", aliases = "setrankcolor", permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 2) {
            player.sendMessage(CC.translate("&cUsage: /rank setcolor (rank-name) (color-code)"));
            return;
        }

        String rankName = args.getArgs(0);
        String color = args.getArgs(1);
        setColor(player, rankName, color);
    }

    private void setColor(Player player, String rankName, String color) {
        if (FlowerCore.getInstance().getRanksManager().getRank(rankName) == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        FlowerCore.getInstance().getRanksManager().getRank(rankName).setColor(color);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.set-color").replace("%rank%", rankName).replace("%color%", color)));
    }
}
