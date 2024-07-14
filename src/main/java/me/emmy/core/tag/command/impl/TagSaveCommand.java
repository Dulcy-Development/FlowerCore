package me.emmy.core.tag.command.impl;

import me.emmy.core.FlowerCore;
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
public class TagSaveCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.save", permission = "flower.tags.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        player.sendMessage(CC.translate("saving tag"));
        FlowerCore.getInstance().getTagRepository().saveToFile();
        player.sendMessage(CC.translate("saved tag"));
    }
}
