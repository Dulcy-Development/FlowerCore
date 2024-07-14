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
public class TagDeleteCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.delete", permission = "flower.tags.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /tag delete (tag-name)"));
            return;
        }

        String tagName = args[0];
        Tag tag = FlowerCore.getInstance().getTagRepository().getTag(tagName);

        if (tag == null) {
            player.sendMessage(CC.translate("&cThat tag does not exist!"));
            return;
        }

        FlowerCore.getInstance().getTagRepository().removeTag(tagName);
        player.sendMessage(CC.translate("&aSuccessfully deleted the &b" + tagName + " &atag!"));
    }
}
