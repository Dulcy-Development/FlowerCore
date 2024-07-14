package me.emmy.core.command.admin.essential;

import me.emmy.core.FlowerCore;
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
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("fly-command.message"))
                    .replace("%status%", player.getAllowFlight() ? enabled : disabled)
            );
            return;
        }

        Player target = player.getServer().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("fly-command.player-not-found")));
            return;
        }

        target.setAllowFlight(!target.getAllowFlight());
        target.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("fly-command.changed-by"))
                .replace("%status%", target.getAllowFlight() ? enabled : disabled)
                .replace("%player%", player.getName())
        );

        player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("fly-command.message-target"))
                .replace("%target%", target.getName())
                .replace("%status%", target.getAllowFlight() ? enabled : disabled)
        );
    }
}