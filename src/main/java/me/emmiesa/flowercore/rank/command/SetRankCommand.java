package me.emmiesa.flowercore.rank.command;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.grant.Grant;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.rank.Rank;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 07/05/2024 - 20:40
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

        Rank rank = FlowerCore.getInstance().getRankRepository().getRank(rankName);
        if (rank == null) {
            sender.sendMessage(CC.translate("&cThat Rank does not exist!"));
            return;
        }

        Date date = new Date();
        date.setTime(System.currentTimeMillis());

        Grant grant = new Grant(date, null, duration, sender.getName(), reason, null, rank.getName(), null,true, Locale.SERVER_NAME);
        FlowerCore.getInstance().getProfileRepository().addGrant(targetPlayer.getUniqueId(), grant);

        UUID targetUUID = targetPlayer.getUniqueId();
        FlowerCore.getInstance().getProfileRepository().getProfile(targetUUID).setRank(rank);
        FlowerCore.getInstance().getMongoService().saveProfile(targetUUID);

        String grantedBy = sender instanceof Player ? FlowerCore.getInstance().getProfileRepository().getProfile(((Player) sender).getUniqueId()).getRank().getColor() + sender.getName() : "&fCONSOLE";
        sender.sendMessage(CC.translate("&aYou have successfully granted " + targetName + " &athe " + rank.getDisplayName() + " &arank!"));
        targetPlayer.sendMessage(CC.translate("&aYour rank has been set to " + rank.getDisplayName() + " &aby &f" + grantedBy + "&a."));
    }
}