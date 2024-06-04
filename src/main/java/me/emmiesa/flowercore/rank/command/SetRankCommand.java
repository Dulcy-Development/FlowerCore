package me.emmiesa.flowercore.rank.command;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.grant.Grant;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.profile.Profile;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 07/05/2024 - 20:40
 */

public class SetRankCommand extends BaseCommand {
    @Override
    @Command(name = "setrank", permission = "flower.ranks.developer", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (command.length() < 4) {
            sender.sendMessage(CC.translate("&cUsage: /setrank (player) (rank-name) (duration) (reason)"));
            return;
        }

        String targetName = args[0];
        String rankName = args[1];
        String duration = args[2];
        String reason = args[3];

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

        Date date = new Date();
        date.setTime(System.currentTimeMillis());

        Grant grant = new Grant(date, null, duration, sender.getName(), reason, null, rank.getName(), null,true, Locale.SERVER_NAME);
        FlowerCore.getInstance().getProfileManager().addGrant(targetPlayer.getUniqueId(), grant);

        UUID targetUUID = targetPlayer.getUniqueId();
        FlowerCore.getInstance().getProfileManager().getProfile(targetUUID).setRank(rank);
        FlowerCore.getInstance().getMongoManager().saveProfile(targetUUID);

        String grantedBy = sender instanceof Player ? FlowerCore.getInstance().getProfileManager().getProfile(((Player) sender).getUniqueId()).getRank().getColor() + sender.getName() : "&fCONSOLE";
        sender.sendMessage(CC.translate("&aYou have successfully granted " + targetName + " &athe " + rank.getDisplayName() + " &arank!"));
        targetPlayer.sendMessage(CC.translate("&aYour rank has been set to " + rank.getDisplayName() + " &aby &f" + grantedBy + "&a."));
    }
}