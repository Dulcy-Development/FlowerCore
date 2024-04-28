package me.emmiesa.flowercore.commands.admin.punishments;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.punishments.Punishment;
import me.emmiesa.flowercore.punishments.PunishmentType;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
