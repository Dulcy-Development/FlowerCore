package me.emmiesa.flowercore.punishment.command.punish;

import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 28/04/2024 - 17:38
 */
public class IPBlacklistCommand extends BaseCommand {

    @Command(name = "blacklist-ip", aliases = {"ipblacklist"}, permission = "flower.punishment.ipblacklist", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
    }
}
