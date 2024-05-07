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

public class TagSettagCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.settag", permission = "flowercore.staff")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 2) {
            player.sendMessage(CC.translate("&cUsage: /tagadmin settag (tag-name) (tag-format)"));
            return;
        }

        String tagName = args[0];
        String tagFormat = args[1];

        Tag tag = FlowerCore.getInstance().getTagsManager().getTag(tagName);

        if (tag == null) {
            player.sendMessage(CC.translate("&cThat Tag does not exist!"));
            return;
        }

        tag.setDisplayName(tagFormat);
        player.sendMessage(CC.translate("&aSuccessfully set the tag format!"));
    }
}
