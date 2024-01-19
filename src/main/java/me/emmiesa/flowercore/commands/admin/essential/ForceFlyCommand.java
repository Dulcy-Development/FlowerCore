package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class ForceFlyCommand extends BaseCommand {

    @Command(name = "forcefly", permission = "flowercore.command.forcefly")
    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        player.setAllowFlight(!player.getAllowFlight());

        if (player.getAllowFlight()) {
            player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("forcefly.enabled")));
        } else {
            player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("forcefly.disabled")));
        }
    }
}
