package me.emmiesa.flowercore.commands.admin.tags.subcommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.tags.Tag;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class TagSetDisplayCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.setdisplayname", aliases = "tag.setdisplay")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 2) {
            player.sendMessage(CC.translate("&cUsage: /tagadmin setdisplayname (tag-name) (display-name)"));
            return;
        }

        String tagName = args[0];
        String displayName = args[1];

        Tag tag = FlowerCore.getInstance().getTagsManager().getTag(tagName);
        if (tag == null) {
            player.sendMessage(CC.translate("&cThat Tag does not exist"));
            return;
        }

        FlowerCore.getInstance().getTagsManager().getTag(tagName).setDisplayName(displayName);
    }
}
