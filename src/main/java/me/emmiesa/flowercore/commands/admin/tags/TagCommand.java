package me.emmiesa.flowercore.commands.admin.tags;

import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import me.emmiesa.flowercore.utils.command.Completer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TagCommand extends BaseCommand {

    @Completer(name = "tag")
    public List<String> tagCompleter(CommandArgs args) {
        List<String> commands = new ArrayList<>();
        if (args.length() == 1) {
            commands.add("create");
            commands.add("delete");
            commands.add("save");
            commands.add("list");

            commands.add("settag");
            commands.add("setcolor");
        }
        return commands;
    }

    @Command(name = "tag", permission = "flowercore.staff")

    @Override
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        player.sendMessage(" ");
        player.sendMessage(CC.FLOWER_BAR_LONG);
        player.sendMessage(CC.translate("&b&lTag Creation Help:"));
        player.sendMessage(CC.translate(" &f● &b/tag create &8<&7name&8> &8| &7Create Tag"));
        player.sendMessage(CC.translate(" &f● &b/tag delete &8<&7name&8> &8| &7Delete Tag"));
        player.sendMessage(CC.translate(" &f● &b/tag save &8<&7name&8> &8| &7Save tags to file."));
        player.sendMessage(CC.translate(" &f● &b/tag list &8| &7See all ranks."));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&b&lTag Customization Help:"));
        player.sendMessage(CC.translate(" &f● &b/tag setTag &8<&7name&8> &8<&7displayName&8>  &8| &7Set the way the tag should look."));
        player.sendMessage(CC.translate(" &f● &b/tag setColor &8<&7name&8> &8<&7color&8> &8| &7Set color."));
        player.sendMessage(CC.FLOWER_BAR_LONG);
        player.sendMessage(" ");
    }
}