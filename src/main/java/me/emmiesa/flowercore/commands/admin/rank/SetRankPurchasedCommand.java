package me.emmiesa.flowercore.commands.admin.rank;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.ranks.Rank;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 07/05/2024 - 21:32
 */

public class SetRankPurchasedCommand extends BaseCommand {
        @Override
        @Command(name = "setrankpurchased", permission = "flower.ranks.developer", inGameOnly = false)
        public void onCommand(CommandArgs command) {
            CommandSender sender = command.getSender();
            String[] args = command.getArgs();

            if (args.length < 2) {
                sender.sendMessage(CC.translate("&cUsage: /setrankpurchased (player) (rank-name)"));
                return;
            }

            String targetName = args[0];
            String rankName = args[1];

            Player targetPlayer = Bukkit.getPlayer(targetName);
            if (targetPlayer == null) {
                sender.sendMessage(CC.translate(Locale.PLAYER_NOT_ONLINE).replace("%player%", args[0]));
                return;
            }

            Rank rank = FlowerCore.getInstance().getRanksManager().getRank(rankName);
            if (rank == null) {
                sender.sendMessage(CC.translate("&cThat Rank does not exist!"));
                return;
            }

            UUID targetUUID = targetPlayer.getUniqueId();
            FlowerCore.getInstance().getPlayerManager().getProfile(targetUUID).setRank(rank);
            Utils.broadcastMessage(CC.translate("&7[&4Alert&7] &r" + FlowerCore.getInstance().getPlayerManager().getProfile(targetUUID).getRank().getColor() + targetName + " &bhas purchased the &r" + rank.getColor() + rankName + " &brank!"));
            Bukkit.getConsoleSender().sendMessage(CC.translate("&7[&4Alert&7] &r" + FlowerCore.getInstance().getPlayerManager().getProfile(targetUUID).getRank().getColor() + targetName + " &bhas purchased the &r" + rank.getColor() + rankName + " &brank!"));
        }
    }
