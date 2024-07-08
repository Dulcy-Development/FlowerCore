package me.emmiesa.flowercore.command.admin.management;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 02/04/2024 - 14:40
 */
public class InfoCommand extends BaseCommand {
    @Override
    @Command(name = "info", aliases = {"playerinfo"}, permission = "flower.command.info", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        if (command.length() != 1) {
            sender.sendMessage(CC.translate("&cUsage: /info (player)"));
            return;
        }

        String target = command.getArgs(0);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);

        UUID playerUUID = offlinePlayer.getUniqueId();

        Document doc = FlowerCore.getInstance().getMongoService().getCollection().find(eq("UUID", playerUUID.toString())).first();
        if (doc == null) {
            sender.sendMessage(CC.translate(" "));
            sender.sendMessage(CC.translate("&cPlayer profile document for &4" + target + " &cnot found."));
            sender.sendMessage(CC.translate(" &f► First Joined: &cHaven't joined before."));
            sender.sendMessage(CC.translate(" &f► Rank: &cNone"));
            sender.sendMessage(CC.translate(" &f► Tag: &cNone"));
            sender.sendMessage(CC.translate(" &7► UUID: &8" + offlinePlayer.getUniqueId()));
            sender.sendMessage(CC.translate(" "));
            return;
        }

        long firstJoined = doc.getLong("firstjoined");
        String firstJoinedDate = new SimpleDateFormat("yyyy-MM-dd - HH:mm:ss").format(new Date(firstJoined));

        String lastSeenDate;
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null && player.isOnline()) {
            lastSeenDate = CC.translate("&aCurrently Online");
        } else {
            long lastSeen = doc.getLong("lastOnline");
            lastSeenDate = new SimpleDateFormat("yyyy-MM-dd - HH:mm:ss").format(new Date(lastSeen));
        }

        sender.sendMessage(CC.translate(""));
        sender.sendMessage(CC.translate("&bPlayer Information for " + FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).getColor() + target + "&b:"));
        sender.sendMessage(CC.translate(" &f► First Joined: &b" + firstJoinedDate));
        sender.sendMessage(CC.translate(" &f► Last Seen: &b" + lastSeenDate));
        sender.sendMessage(CC.translate(" &f► Rank: &b" + FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).getDisplayName()));
        sender.sendMessage(CC.translate(" &f► Tag: &b" + (FlowerCore.getInstance().getProfileRepository().getTag(playerUUID) != null ? FlowerCore.getInstance().getProfileRepository().getTag(playerUUID).getColor() + FlowerCore.getInstance().getProfileRepository().getTag(playerUUID).getName() : "&cNone")));
        sender.sendMessage(CC.translate(" &7► UUID: &8" + offlinePlayer.getUniqueId()));
        sender.sendMessage(CC.translate(""));
    }
}