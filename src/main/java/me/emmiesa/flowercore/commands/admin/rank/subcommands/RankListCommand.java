package me.emmiesa.flowercore.commands.admin.rank.subcommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.menus.ranklist.RankListMenu;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.chat.StringUtil;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class RankListCommand extends BaseCommand {

    @Command(name = "rank.list", aliases = {"ranklist", "listranks"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (FlowerCore.getInstance().getConfig("settings.yml").getBoolean("rank-settings.list.send-as-message")) {
            sendAsMessage(player);
        }
        if (FlowerCore.getInstance().getConfig("settings.yml").getBoolean("rank-settings.list.open-menu")) {
            openListMenu(player);
        }
    }

    public void openListMenu(Player player) {
        new RankListMenu().openMenu(player);
    }

    public void sendAsMessage(Player player) {
        List<Rank> ranks = FlowerCore.getInstance().getRanksManager().getRanks();

        int maxLength = ranks.stream()
                .mapToInt(rank -> ChatColor.stripColor(rank.getDisplayName()).length())
                .max()
                .orElse(0);

        player.sendMessage(" ");
        player.sendMessage(CC.FLOWER_BAR);
        player.sendMessage(CC.translate("   &b&lRank list &f(" + FlowerCore.getInstance().getConfig("ranks.yml").getConfigurationSection("ranks").getKeys(false).size()) + CC.translate("&f)"));

        for (Rank rank : ranks) {
            String paddedName = StringUtil.padRight(rank.getColor() + rank.getName() + " &8&l┃&r " + rank.getPrefix(), maxLength);
            String rankLine = "    &f► " + paddedName;
            player.sendMessage(CC.translate(rankLine));
        }

        player.sendMessage(CC.FLOWER_BAR);
        player.sendMessage(" ");
    }
}
