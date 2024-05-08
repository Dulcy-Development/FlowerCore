package me.emmiesa.flowercore.commands.admin.tags;

import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import me.emmiesa.flowercore.utils.command.Completer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class TagAdminCommand extends BaseCommand {

    @Completer(name = "tagadmin")
    public List<String> tagCompleter(CommandArgs args) {
        List<String> commands = new ArrayList<>();
        if (args.length() == 1) {
            if (args.getPlayer().hasPermission("flowercore.admin")) {
                commands.add("create");
                commands.add("delete");
                commands.add("save");
                commands.add("list");

                commands.add("settag");
                commands.add("seticon");
                commands.add("setdisplayname");
            }
        }
        return commands;
    }

    @Command(name = "tagadmin", permission = "flowercore.staff")

    @Override
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
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