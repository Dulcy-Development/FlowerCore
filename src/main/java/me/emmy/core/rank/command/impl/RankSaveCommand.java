package me.emmy.core.rank.command.impl;

import me.emmy.core.FlowerCore;
import me.emmy.core.locale.Locale;
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
public class RankSaveCommand extends BaseCommand {
    @Override
    @Command(name = "rank.save", aliases = {"saveranks"}, permission = "flower.ranks.developer")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        player.sendMessage(CC.translate(Locale.RANK_SAVING));
        FlowerCore.getInstance().getRankRepository().saveToFile();
        player.sendMessage(CC.translate(Locale.RANK_SAVED));
    }
}
