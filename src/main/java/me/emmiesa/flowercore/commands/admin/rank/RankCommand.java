package me.emmiesa.flowercore.commands.admin.rank;

import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class RankCommand extends BaseCommand {

    @Command(name = "rank", permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        player.sendMessage(" ");
        player.sendMessage(CC.CHAT_BAR);
        player.sendMessage(CC.translate("&b&lRank Creation Help:"));
        player.sendMessage(CC.translate(" &7┃ &b/rank create &8<&7name&8> &8| &7Create rank"));
        player.sendMessage(CC.translate(" &7┃ &b/rank delete &8<&7name&8> &8| &7Delete Rank"));
        player.sendMessage(CC.translate(" &7┃ &b/rank save &8<&7name&8> &8| &7Save ranks to file."));
        player.sendMessage(CC.translate(" &7┃ &b/rank list &8| &7See all ranks."));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&b&lRank Customization Help:"));
        player.sendMessage(CC.translate(" &7┃ &b/rank setDisplay &8<&7name&8> &8<&7displayName&8>  &8| &7Set display name."));
        player.sendMessage(CC.translate(" &7┃ &b/rank setIcon &8<&7name&8>  &8| &7Set icon."));
        player.sendMessage(CC.translate(" &7┃ &b/rank setPrefix &8<&7name&8> &8<&7prefix&8>  &8| &7Set prefix."));
        player.sendMessage(CC.translate(" &7┃ &b/rank setSuffix &8<&7name&8> &8<&7suffix&8>  &8| &7Set suffix."));
        player.sendMessage(CC.translate(" &7┃ &b/rank setPriority &8<&7name&8> &8<&7priority&8>  &8| &7Set priority."));
        player.sendMessage(CC.translate(" &7┃ &b/rank setDefault &8<&7name&8> &8<&7true/false&8>  &8| &7Set default."));
        player.sendMessage(CC.translate(" &7┃ &b/rank setStaff &8<&7name&8> &8<&7true/false&8>  &8| &7Set staff."));
        player.sendMessage(CC.translate(" &7┃ &b/rank addPermissions &8<&7name&8> &8<&7permission&8>  &8| &7Add permission."));
        player.sendMessage(CC.CHAT_BAR);
        player.sendMessage(" ");
    }
}