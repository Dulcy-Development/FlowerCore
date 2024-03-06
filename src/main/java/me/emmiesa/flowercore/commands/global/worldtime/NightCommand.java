package me.emmiesa.flowercore.commands.global.worldtime;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class NightCommand extends BaseCommand {
    @Override
    @Command(name = "night")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        player.setPlayerTime(18000L, false);
        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("time-changer.night")));
    }
}