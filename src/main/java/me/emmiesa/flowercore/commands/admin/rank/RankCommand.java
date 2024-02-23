package me.emmiesa.flowercore.commands.admin.rank;

import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import me.emmiesa.flowercore.utils.command.Completer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RankCommand extends BaseCommand {

    @Completer(name = "rank")
    public List<String> rankCompleter(CommandArgs args) {
        List<String> add = new ArrayList<>();
        if (args.length() == 1) {
            add.add("create");
            add.add("delete");
            add.add("save");
            add.add("list");

            add.add("setdisplay");
            add.add("setcolor");
            add.add("seticon");
            add.add("setprefix");
            add.add("setsuffix");
            add.add("setpriority");
            add.add("setdefault");
            add.add("setstaff");
            add.add("addperm");
        }
        return add;
    }

    @Command(name = "rank", permission = "flowercore.staff")

    @Override
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        player.sendMessage(" ");
        player.sendMessage(CC.FLOWER_BAR_LONG);
        player.sendMessage(CC.translate("&b&lRank Creation Help:"));
        player.sendMessage(CC.translate(" &f● &b/rank create &8<&7name&8> &8| &7Create rank"));
        player.sendMessage(CC.translate(" &f● &b/rank delete &8<&7name&8> &8| &7Delete Rank"));
        player.sendMessage(CC.translate(" &f● &b/rank save &8<&7name&8> &8| &7Save ranks to file."));
        player.sendMessage(CC.translate(" &f● &b/rank list &8| &7See all ranks."));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&b&lRank Customization Help:"));
        player.sendMessage(CC.translate(" &f● &b/rank setDisplay &8<&7name&8> &8<&7displayName&8>  &8| &7Set display name."));
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