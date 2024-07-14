package me.emmy.core.tag.command;

import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import me.emmy.core.api.command.annotation.Completer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TagAdminCommand extends BaseCommand {
    @Completer(name = "tagadmin")
    public List<String> tagCompleter(CommandArgs command) {
        List<String> commands = new ArrayList<>();
        if (command.length() == 1 && command.getPlayer().hasPermission("flower.tags.developer")) {
            commands.add("create");
            commands.add("delete");
            commands.add("save");
            commands.add("list");

            commands.add("settag");
            commands.add("seticon");
            commands.add("setdisplayname");
        }
        return commands;
    }

    @Override
    @Command(name = "tagadmin", permission = "flower.tags.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        player.sendMessage(" ");
        player.sendMessage(CC.FLOWER_BAR_LONG);
        player.sendMessage(CC.translate("&b&lTag Creation Help:"));
        player.sendMessage(CC.translate(" &f● &b/tagadmin create &8<&7name&8> &8| &7Create Tag"));
        player.sendMessage(CC.translate(" &f● &b/tagadmin delete &8<&7name&8> &8| &7Delete Tag"));
        player.sendMessage(CC.translate(" &f● &b/tagadmin save &8<&7name&8> &8| &7Save tags to file."));
        player.sendMessage(CC.translate(" &f● &b/tagadmin list &8| &7See all tags."));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&b&lTag Customization Help:"));
        player.sendMessage(CC.translate(" &f● &b/tagadmin setTag &8<&7name&8> &8<&7tag-format&8>  &8| &7Set the way the tag should look."));
        player.sendMessage(CC.translate(" &f● &b/tagadmin setIcon &8<&7name&8> &8| &7Set icon."));
        player.sendMessage(CC.translate(" &f● &b/tagadmin setDisplayname &8<&7name&8> &8<&7displayname&8> &8| &7Set display name."));
        player.sendMessage(CC.FLOWER_BAR_LONG);
        player.sendMessage(" ");
    }
}