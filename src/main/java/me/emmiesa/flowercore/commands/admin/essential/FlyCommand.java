package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class FlyCommand extends BaseCommand {

    @Command(name = "fly", permission = "flower.command.fly")
    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();

        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
            player.setAllowFlight(!player.getAllowFlight());

            if (player.getAllowFlight()) {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("fly.enabled")));
            } else {
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("fly.disabled")));
            }
        } else {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("fly.not-in-survival")));
        }
    }
}