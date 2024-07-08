package me.emmiesa.flowercore.punishment.command.punish;

import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 24/04/2024 - 23:11
 */
public class IPBanCommand extends BaseCommand {

    @Command(name = "ban-ip", aliases = {"ipban"}, permission = "flower.punishment.ipban", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
    }
}
