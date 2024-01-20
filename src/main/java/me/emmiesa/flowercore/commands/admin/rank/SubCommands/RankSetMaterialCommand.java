package me.emmiesa.flowercore.commands.admin.rank.SubCommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Lang;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RankSetMaterialCommand extends BaseCommand {

    @Command(name = "rank.setmaterial", aliases = {"setrankmaterial"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 1) {
            player.sendMessage(CC.translate("&cUsage: /rank setMaterial (rank-name)"));
            return;
        }

        String rankName = args.getArgs(0);

        setMaterial(player, rankName);
    }

    public void setMaterial(Player player, String rankName) {
        Rank rank = FlowerCore.instance.getRanksManager().getRank(rankName);

        if (rank == null) {
            player.sendMessage(CC.translate(Lang.RANK_NOT_FOUND).replace("%rank%", rankName));
            return;
        }

        if (player.getItemInHand().getType().equals(Material.AIR)) {
            player.sendMessage(CC.translate("&4ERROR &8| &cYou need to be holding an item!"));
            return;
        }

        rank.setIcon(player.getItemInHand().getType());
        player.sendMessage(CC.translate("&aSuccessfully set rank icon!"));
    }
}