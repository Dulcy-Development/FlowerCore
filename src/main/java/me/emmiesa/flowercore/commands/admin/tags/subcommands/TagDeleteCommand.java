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

public class TagDeleteCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.delete", permission = "flowercore.staff")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        String tagName = command.getArgs()[0];
        Tag tag = FlowerCore.getInstance().getTagsManager().getTag(tagName);

        if (command.getArgs().length < 1) {
            player.sendMessage(CC.translate("&cUsage: /tag delete (tag-name)"));
            return;
        }

        if (tag == null) {
            player.sendMessage(CC.translate("&cThat tag does not exist!"));
            return;
        }

        FlowerCore.getInstance().getTagsManager().removeTag(tagName);
        player.sendMessage(CC.translate("&aSuccessfully deleted the &b" + tagName + " &atag!"));
    }
}
