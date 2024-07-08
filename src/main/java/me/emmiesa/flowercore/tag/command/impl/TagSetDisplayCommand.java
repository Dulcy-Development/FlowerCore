package me.emmiesa.flowercore.tag.command.impl;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.tag.Tag;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TagSetDisplayCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.setdisplayname", aliases = "tag.setdisplay", permission = "flower.tags.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 2) {
            player.sendMessage(CC.translate("&cUsage: /tagadmin setdisplayname (tag-name) (display-name)"));
            return;
        }

        String tagName = args[0];
        String displayName = args[1];

        Tag tag = FlowerCore.getInstance().getTagRepository().getTag(tagName);
        if (tag == null) {
            player.sendMessage(CC.translate("&cThat Tag does not exist"));
            return;
        }

        FlowerCore.getInstance().getTagRepository().getTag(tagName).setDisplayName(displayName);
    }
}
