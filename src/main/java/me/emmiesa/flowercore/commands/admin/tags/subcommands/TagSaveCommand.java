package me.emmiesa.flowercore.commands.admin.tags.subcommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Locale;
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

public class TagSaveCommand extends BaseCommand {
    @Override
    @Command(name = "tagadmin.save", permission = "flowercore.staff")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        player.sendMessage(CC.translate("saving tag"));
        FlowerCore.getInstance().getTagsManager().saveToFile();
        player.sendMessage(CC.translate("saved tag"));
    }
}
