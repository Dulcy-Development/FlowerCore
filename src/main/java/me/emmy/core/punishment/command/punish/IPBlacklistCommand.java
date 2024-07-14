package me.emmy.core.punishment.command.punish;

import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
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
