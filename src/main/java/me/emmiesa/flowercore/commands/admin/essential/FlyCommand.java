package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class FlyCommand extends BaseCommand {

    @Command(name = "fly", permission = "core.essentials.fly")
    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();

        if (player.getGameMode() == GameMode.SURVIVAL) {
            player.setAllowFlight(!player.getAllowFlight());

            if (player.getAllowFlight()) {
                player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("fly.enabled")));
            } else {
                player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("fly.disabled")));
            }
        } else {
            player.sendMessage(CC.translate("&8[&7Consider using this only in gamemode survival to avoid issues&8]"));
            player.setAllowFlight(!player.getAllowFlight());
        }
    }
}