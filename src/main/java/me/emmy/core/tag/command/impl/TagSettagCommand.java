package me.emmy.core.tag.command.impl;

import me.emmy.core.FlowerCore;
import me.emmy.core.tag.Tag;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TagSettagCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.settag", permission = "flower.tags.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 2) {
            player.sendMessage(CC.translate("&cUsage: /tagadmin settag (tag-name) (tag-format)"));
            return;
        }

        String tagName = args[0];
        String tagFormat = args[1];

        Tag tag = FlowerCore.getInstance().getTagRepository().getTag(tagName);

        if (tag == null) {
            player.sendMessage(CC.translate("&cThat Tag does not exist!"));
            return;
        }

        tag.setDisplayName(tagFormat);
        player.sendMessage(CC.translate("&aSuccessfully set the tag format!"));
    }
}
