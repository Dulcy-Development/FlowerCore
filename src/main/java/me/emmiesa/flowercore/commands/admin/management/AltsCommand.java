package me.emmiesa.flowercore.commands.admin.management;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.player.PlayerUtil;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bson.Document;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class AltsCommand extends BaseCommand {

    @Override
    @Command(name = "alts", permission = "flower.command.alts")
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        if (command.getArgs().length != 1) {
            sender.sendMessage(CC.translate("&cUsage: /alts (player)"));
            return;
        }

        String targetPlayerName = command.getArgs(0);

        Document targetPlayerDoc = PlayerUtil.getPlayerDocument(sender, targetPlayerName);
        if (targetPlayerDoc == null) {
            sender.sendMessage(CC.translate("&4" + targetPlayerName + " &cis invalid."));
            return;
        }

        String targetPlayerIpAddress = PlayerUtil.getPlayerIpAddressFromDocument(targetPlayerDoc);
        UUID targetPlayerUUID = PlayerUtil.getPlayerUUIDFromDocument(targetPlayerName);

        MongoCollection<Document> collection = FlowerCore.getInstance().getMongoManager().getCollection();
        FindIterable<Document> documents = collection.find(eq("currentIpAddress", targetPlayerIpAddress));

        List<String> alts = new ArrayList<>();

        for (Document doc : documents) {
            String username = doc.getString("username");
            alts.add(username);
        }

        alts.removeIf(alt -> alt.equalsIgnoreCase(targetPlayerName));

        if (alts.isEmpty()) {
            sender.sendMessage("");
            sender.sendMessage(CC.translate("&c" + FlowerCore.getInstance().getPlayerManager().getRank(targetPlayerUUID).getColor() + targetPlayerName + " &chas no alt accounts!"));
            sender.sendMessage("");
            return;
        }

        sender.sendMessage(" ");
        sender.sendMessage(CC.translate("&c" + FlowerCore.getInstance().getPlayerManager().getRank(targetPlayerUUID).getColor() + targetPlayerName + "&c's alt accounts:"));

        for (String alt : alts) {
            sender.sendMessage(CC.translate("&c► &f" + alt));
        }

        sender.sendMessage(" ");
    }

}
