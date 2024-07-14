package me.emmy.core.tag.command.impl;

import me.emmy.core.FlowerCore;
import me.emmy.core.tag.Tag;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class TagCreateCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.create", permission = "flower.tags.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (command.length() > 0) {
            create(player, command.getArgs(0));
        } else {
            player.sendMessage(CC.translate("&cUsage: /tag create (tag-name)"));
        }
    }

    public void create(Player player, String tagName) {
        List<String> permissions = Collections.singletonList("none");
        Tag tag = new Tag(tagName, "&7", Material.NAME_TAG, "&b");

        FlowerCore.getInstance().getTagRepository().getTags().add(tag);

        player.sendMessage(CC.translate("tag created"));
        //player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("rank.created")).replace("%rank%", tagName));
        player.sendMessage(CC.translate("dont forget to save the tag"));
        //if (FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getBoolean("rank.save-reminder.enabled")) {
        //    player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("rank.save-reminder.message")));
        //}
    }
}
