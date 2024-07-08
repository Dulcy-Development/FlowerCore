package me.emmiesa.flowercore.command.global.worldtime;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class SunsetCommand extends BaseCommand {
    @Override
    @Command(name = "sunset")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        player.setPlayerTime(12000, false);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("time-changer.sunset")));
    }
}