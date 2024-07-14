package me.emmy.core.tag.command.impl;

import me.emmy.core.FlowerCore;
import me.emmy.core.tag.Tag;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.utils.chat.StringUtil;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TagListCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.list", permission = "flower.tags.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        sendAsMessage(player);
    }

    public void sendAsMessage(Player player) {
        List<Tag> tags = FlowerCore.getInstance().getTagRepository().getTags();

        int maxLength = tags.stream()
                .mapToInt(tag -> ChatColor.stripColor(tag.getDisplayName()).length())
                .max()
                .orElse(0);

        player.sendMessage(" ");
        player.sendMessage(CC.FLOWER_BAR);
        player.sendMessage(CC.translate("   &b&lTag list &f(" + FlowerCore.getInstance().getConfigHandler().getConfig("tags.yml").getConfigurationSection("tags").getKeys(false).size()) + CC.translate("&f)"));

        for (Tag tag : tags) {
            String paddedName = StringUtil.padRight(tag.getColor() + tag.getName() + " &8&l┃&r " + tag.getDisplayName(), maxLength);
            String rankLine = "    &f► " + paddedName;
            player.sendMessage(CC.translate(rankLine));
        }

        player.sendMessage(CC.FLOWER_BAR);
        player.sendMessage(" ");
    }
}
