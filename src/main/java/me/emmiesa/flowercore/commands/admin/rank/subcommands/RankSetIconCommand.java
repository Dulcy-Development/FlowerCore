package me.emmiesa.flowercore.commands.admin.rank.subcommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RankSetIconCommand extends BaseCommand {

    @Command(name = "rank.seticon", aliases = {"setrankicon"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 1) {
            player.sendMessage(CC.translate("&cUsage: /rank setIcon (rank-name)"));
            return;
        }

        String rankName = args.getArgs(0);

        setMaterial(player, rankName);
    }

    public void setMaterial(Player player, String rankName) {
        Rank rank = FlowerCore.getInstance().getRanksManager().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Locale.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        if (player.getItemInHand().getType().equals(Material.AIR)) {
            player.sendMessage(CC.translate("&cYou need to be holding an item!"));
            return;
        }

        rank.setIcon(player.getItemInHand().getType());
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.set-icon")
                        .replace("%rank%", rankName))
                //.replace("%icon%", player.getItemInHand().getItemMeta().getDisplayName())
        );
    }
}