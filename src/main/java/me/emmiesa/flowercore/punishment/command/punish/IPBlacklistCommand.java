package me.emmiesa.flowercore.punishment.command.punish;

import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 28/04/2024 - 17:38
 */

public class IPBlacklistCommand extends BaseCommand {

    @Command(name = "blacklist-ip", aliases = {"ipblacklist"}, permission = "flower.punishment.ipblacklist", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
    }
}