package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import me.emmiesa.flowercore.utils.command.Completer;
import me.emmiesa.flowercore.utils.others.NumberUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/*
 * Credit: Zoom core
 */

public class SpeedCommand extends BaseCommand {

    @Completer(name = "speed")
    public List<String> speedCompleter(CommandArgs args) {
        List<String> add = new ArrayList<>();
        if (args.length() == 1) {
            add.add("fly");
            add.add("walk");
        }
        return add;
    }

    @Command(name = "speed", permission = "flowercore.cmd.speed")

    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /speed fly &4┃ &cwalk (value). To reset, do /defaultspeed."));
            return;
        }

        int speedVelocity = Integer.parseInt(args[1]);
        switch (args[0]) {
            case "fly":
                if (!NumberUtils.checkInt(args[1])) {
                    player.sendMessage(CC.translate("&cPlease use Numbers! ( 1, 2, 3, ect.. )"));
                    return;
                }
                if (speedVelocity < 1 || speedVelocity > 10) {
                    player.sendMessage(CC.translate("&cPlease enter a value between 1 and 10"));
                    return;
                }
                player.setFlySpeed(speedVelocity * 0.1F);
                player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("speed-set.fly").replace("%value%", String.valueOf(speedVelocity))));
                break;
            case "walk":
                if (!NumberUtils.checkInt(args[1])) {
                    player.sendMessage(CC.translate("&cPlease use Numbers! ( 1, 2, 3, ect.. )"));
                    return;
                }
                if (speedVelocity < 1 || speedVelocity > 10) {
                    player.sendMessage(CC.translate("&cPlease enter a value between 1 and 10"));
                    return;
                }
                player.setWalkSpeed(speedVelocity * 0.1F);
                player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("speed-set.walk").replace("%value%", String.valueOf(speedVelocity))));
                break;
            default:
                break;
        }
    }
}
