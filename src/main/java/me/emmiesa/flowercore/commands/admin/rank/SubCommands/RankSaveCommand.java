package me.emmiesa.flowercore.commands.admin.rank.SubCommands;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Lang;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class RankSaveCommand extends BaseCommand {

    @Command(name = "rank.save", aliases = {"saverank"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        player.sendMessage(CC.translate(Lang.RANK_SAVING));
        FlowerCore.instance.getRanksManager().saveToFile();
        player.sendMessage(CC.translate(Lang.RANK_SAVED));
    }
}
