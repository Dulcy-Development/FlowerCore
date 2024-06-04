package me.emmiesa.flowercore.command.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
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

public class ForceFlyCommand extends BaseCommand {

    @Command(name = "forcefly", permission = "flower.command.forcefly")
    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        player.setAllowFlight(!player.getAllowFlight());

        if (player.getAllowFlight()) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("forcefly.enabled")));
        } else {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("forcefly.disabled")));
        }
    }
}
