package me.emmiesa.flowercore.tag.command.impl;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.tag.Tag;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TagSetIconCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.seticon", permission = "flower.tags.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 2) {
            player.sendMessage(CC.translate("&cUsage: /tagadmin seticon (tag-name)"));
            return;
        }

        String tagName = args[0];

        Tag tag = FlowerCore.getInstance().getTagRepository().getTag(tagName);
        if (tag == null) {
            player.sendMessage(CC.translate("&cThat Tag does not exist"));
            return;
        }

        if (player.getInventory().getItemInHand().getType() == Material.AIR) {
            player.sendMessage(CC.translate("&cYou must be holding an item to set the icon!"));
            return;
        }

        tag.setIcon(player.getInventory().getItemInHand().getType());
        player.sendMessage(CC.translate("&aSuccessfully set the icon for the &b" + tagName + " &atag!"));
    }
}
