package me.emmiesa.flowercore.commands.admin.server;

import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class InstanceCommand extends BaseCommand {

    @Command(name = "instance", aliases = "serverdetails", permission = "flower.command.instance", inGameOnly = false)

    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        sender.sendMessage(" ");
        sender.sendMessage(CC.translate("&b&lServer Info"));
        sender.sendMessage(" ");
        sender.sendMessage(CC.translate("&8 ┃ &7Server&f: &b"));
        sender.sendMessage(CC.translate("  &b" + Locale.SERVER_NAME));
        sender.sendMessage(CC.translate("&8 ┃ &7Spigot&f:"));
        sender.sendMessage(CC.translate("  &b" + Bukkit.getServer().getVersion()));
        sender.sendMessage(CC.translate("&8 ┃ &7Players&f: "));
        sender.sendMessage(CC.translate("  &b" + Bukkit.getServer().getOnlinePlayers().size() + "/" + Bukkit.getServer().getMaxPlayers()));
        sender.sendMessage(CC.translate(" "));
    }
}