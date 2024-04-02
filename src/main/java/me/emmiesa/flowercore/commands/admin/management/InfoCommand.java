package me.emmiesa.flowercore.commands.admin.management;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 02/04/2024 - 14:40
 */

public class InfoCommand extends BaseCommand {
    @Override
    @Command(name = "info", aliases = {"playerinfo"}, permission = "flowercore.staff", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        if (command.length() != 1) {
            sender.sendMessage(CC.translate("&cUsage: /info (player)"));
            return;
        }

        String target = command.getArgs(0);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);

        UUID playerUUID = offlinePlayer.getUniqueId();

        Document doc = FlowerCore.getInstance().getMongoManager().getCollection().find(eq("UUID", playerUUID.toString())).first();
        if (doc == null) {
            sender.sendMessage(CC.translate(" "));
            sender.sendMessage(CC.translate("&cPlayer profile document for &4" + target + " &cnot found."));
            sender.sendMessage(CC.translate(" &f► First Joined: &cHaven't joined before."));
            sender.sendMessage(CC.translate(" &f► UUID: &c" + offlinePlayer.getUniqueId()));
            sender.sendMessage(CC.translate(" &f► Rank: &cNone"));
            sender.sendMessage(CC.translate(" &f► Tag: &cNone"));
            sender.sendMessage(CC.translate(" "));
            return;
        }

        long firstJoined = doc.getLong("firstjoined");
        String firstJoinedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(firstJoined));

        sender.sendMessage(CC.translate(""));
        sender.sendMessage(CC.translate("&bPlayer Information for " + FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getColor() + target + "&b:"));
        sender.sendMessage(CC.translate(" &f► First Joined: &b" + firstJoinedDate));
        sender.sendMessage(CC.translate(" &f► UUID: &b" + offlinePlayer.getUniqueId()));
        sender.sendMessage(CC.translate(" &f► Rank: &b" + FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getDisplayName()));
        sender.sendMessage(CC.translate(" &f► Tag: &b" + (FlowerCore.getInstance().getPlayerManager().getTag(playerUUID) != null ? FlowerCore.getInstance().getPlayerManager().getTag(playerUUID).getColor() + FlowerCore.getInstance().getPlayerManager().getTag(playerUUID).getName() : "&cNone")));
        sender.sendMessage(CC.translate(""));
    }
}