package me.emmiesa.flowercore.rank.command;

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

public class RankCommand extends BaseCommand {

    @Completer(name = "rank")
    public List<String> rankCompleter(CommandArgs args) {
        List<String> commands = new ArrayList<>();
        if (args.length() == 1) {
            if (args.getPlayer().hasPermission("flower.ranks.developer")) {
                commands.add("create");
                commands.add("delete");
                commands.add("save");
                commands.add("list");

                commands.add("setdisplay");
                commands.add("setcolor");
                commands.add("seticon");
                commands.add("setprefix");
                commands.add("setsuffix");
                commands.add("setpriority");
                commands.add("setdefault");
                commands.add("setstaff");
                commands.add("addperm");
            }
        }
        return commands;
    }

    @Command(name = "rank", permission = "flower.ranks.developer")

    @Override
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        player.sendMessage(" ");
        player.sendMessage(CC.FLOWER_BAR_LONG);
        player.sendMessage(CC.translate("&b&lRank Creation Help:"));
        player.sendMessage(CC.translate(" &f● &b/rank create &8<&7name&8> &8| &7Create a rank"));
        player.sendMessage(CC.translate(" &f● &b/rank delete &8<&7name&8> &8| &7Delete a Rank"));
        player.sendMessage(CC.translate(" &f● &b/rank save &8| &7Save ranks to config."));
        player.sendMessage(CC.translate(" &f● &b/rank list &8| &7See all ranks."));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&b&lRank Customization Help:"));
        player.sendMessage(CC.translate(" &f● &b/rank setDisplayName &8<&7name&8> &8<&7displayName&8>  &8| &7Set display name."));
        player.sendMessage(CC.translate(" &f● &b/rank setColor &8<&7name&8> &8<&7color-code&8>  &8| &7Set rank color."));
        player.sendMessage(CC.translate(" &f● &b/rank setIcon &8<&7name&8>  &8| &7Set icon."));
        player.sendMessage(CC.translate(" &f● &b/rank setPrefix &8<&7name&8> &8<&7prefix&8>  &8| &7Set prefix."));
        player.sendMessage(CC.translate(" &f● &b/rank setSuffix &8<&7name&8> &8<&7suffix&8>  &8| &7Set suffix."));
        player.sendMessage(CC.translate(" &f● &b/rank setPriority &8<&7name&8> &8<&7priority&8>  &8| &7Set priority."));
        player.sendMessage(CC.translate(" &f● &b/rank setDefault &8<&7name&8> &8<&7true/false&8>  &8| &7Set default."));
        player.sendMessage(CC.translate(" &f● &b/rank setStaff &8<&7name&8> &8<&7true/false&8>  &8| &7Set staff."));
        player.sendMessage(CC.translate(" &f● &b/rank addPermissions &8<&7name&8> &8<&7permission&8>  &8| &7Add permission."));
        player.sendMessage(CC.FLOWER_BAR_LONG);
        player.sendMessage(" ");
    }
}