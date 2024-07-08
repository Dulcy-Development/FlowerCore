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
