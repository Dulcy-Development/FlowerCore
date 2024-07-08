package me.emmiesa.flowercore.command.global.worldtime;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class DayCommand extends BaseCommand {
    @Override
    @Command(name = "day")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        player.setPlayerTime(6000L, false);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("time-changer.day")));
    }
}
