package me.emmiesa.flowercore.command.admin.essential;

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
public class FlyCommand extends BaseCommand {
    @Override
    @Command(name = "fly", aliases = {"flight"}, permission = "delta.fly")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        String enabled = CC.translate("&aenabled");
        String disabled = CC.translate("&cdisabled");

        if (args.length < 1) {
            player.setAllowFlight(!player.getAllowFlight());
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfigByName("messages.yml").getString("fly-command.message"))
                    .replace("%status%", player.getAllowFlight() ? enabled : disabled)
            );
            return;
        }

        Player target = player.getServer().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfigByName("messages.yml").getString("fly-command.player-not-found")));
            return;
        }

        target.setAllowFlight(!target.getAllowFlight());
        target.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfigByName("messages.yml").getString("fly-command.changed-by"))
                .replace("%status%", target.getAllowFlight() ? enabled : disabled)
                .replace("%player%", player.getName())
        );

        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfigByName("messages.yml").getString("fly-command.message-target"))
                .replace("%target%", target.getName())
                .replace("%status%", target.getAllowFlight() ? enabled : disabled)
        );
    }
}