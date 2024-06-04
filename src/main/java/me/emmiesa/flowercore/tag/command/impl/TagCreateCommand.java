package me.emmiesa.flowercore.tag.command.impl;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.tag.Tag;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class TagCreateCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.create", permission = "flower.tags.developer")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() > 0) {
            create(player, args.getArgs(0));
        } else {
            player.sendMessage(CC.translate("&cUsage: /tag create (tag-name)"));
        }
    }

    public void create(Player player, String tagName) {
        List<String> permissions = Collections.singletonList("none");
        Tag tag = new Tag(tagName, "&7", Material.NAME_TAG, "&b");

        FlowerCore.getInstance().getTagsManager().getTags().add(tag);

        player.sendMessage(CC.translate("tag created"));
        //player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.created")).replace("%rank%", tagName));
        player.sendMessage(CC.translate("dont forget to save the tag"));
        //if (FlowerCore.getInstance().getConfig("messages.yml").getBoolean("rank.save-reminder.enabled")) {
        //    player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("rank.save-reminder.message")));
        //}
    }
}
