package me.emmiesa.flowercore.commands.admin.tags.subcommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.tags.Tag;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.chat.StringUtil;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class TagListCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.list", permission = "flower.staff")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        sendAsMessage(player);
    }

    public void sendAsMessage(Player player) {
        List<Tag> tags = FlowerCore.getInstance().getTagsManager().getTags();

        int maxLength = tags.stream()
                .mapToInt(tag -> ChatColor.stripColor(tag.getDisplayName()).length())
                .max()
                .orElse(0);

        player.sendMessage(" ");
        player.sendMessage(CC.FLOWER_BAR);
        player.sendMessage(CC.translate("   &b&lTag list &f(" + FlowerCore.getInstance().getConfig("tags.yml").getConfigurationSection("tags").getKeys(false).size()) + CC.translate("&f)"));

        for (Tag tag : tags) {
            String paddedName = StringUtil.padRight(tag.getColor() + tag.getName() + " &8&l┃&r " + tag.getDisplayName(), maxLength);
            String rankLine = "    &f► " + paddedName;
            player.sendMessage(CC.translate(rankLine));
        }

        player.sendMessage(CC.FLOWER_BAR);
        player.sendMessage(" ");
    }
}
