package me.emmiesa.flowercore.commands.admin.rank.subcommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
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

public class RankSaveCommand extends BaseCommand {

    @Command(name = "rank.save", aliases = {"saveranks"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        player.sendMessage(CC.translate(Locale.RANK_SAVING));
        FlowerCore.getInstance().getRanksManager().saveToFile();
        player.sendMessage(CC.translate(Locale.RANK_SAVED));
    }
}
