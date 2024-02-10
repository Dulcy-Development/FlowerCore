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
            add.add("reset");
        }
        return add;
    }

    @Command(name = "speed", permission = "flowercore.cmd.speed")

    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length == 0) {
            player.sendMessage(" ");
            player.sendMessage(CC.FLOWER_BAR_LONG);
            player.sendMessage(CC.translate("&b&lSpeed Command Help:"));
            player.sendMessage(CC.translate(" &f● &b/speed fly &8<&7value&8> &8| &7Set your flying speed."));
            player.sendMessage(CC.translate(" &f● &b/speed walk &8<&7value&8> &8| &7Set your walking speed."));
            player.sendMessage(CC.translate(" &f● &b/speed reset &8| &7Reset your speed"));
            player.sendMessage(CC.FLOWER_BAR_LONG);
            player.sendMessage(" ");
            return;
        }

        switch (args[0]) {
            case "fly":
            case "walk":
                if (args.length < 2) {
                    player.sendMessage(CC.translate("&cPlease specify a speed value."));
                    return;
                }
                if (!NumberUtils.checkInt(args[1])) {
                    player.sendMessage(CC.translate("&cPlease use Numbers! ( 1, 2, 3, etc.. )"));
                    return;
                }
                int speedVelocity = Integer.parseInt(args[1]);
                if (speedVelocity < 1 || speedVelocity > 10) {
                    player.sendMessage(CC.translate("&cPlease enter a value between 1 and 10"));
                    return;
                }
                if (args[0].equals("fly")) {
                    player.setFlySpeed(speedVelocity * 0.1F);
                    player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("speed-set.fly").replace("%value%", String.valueOf(speedVelocity))));
                } else {
                    player.setWalkSpeed(speedVelocity * 0.1F);
                    player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("speed-set.walk").replace("%value%", String.valueOf(speedVelocity))));
                }
                break;
            case "reset":
                player.setFlySpeed(0.1F);
                player.setWalkSpeed(0.2F);
                player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("speed-set.reset")));
                break;
            default:
                player.sendMessage(" ");
                player.sendMessage(CC.FLOWER_BAR_LONG);
                player.sendMessage(CC.translate("&b&lSpeed Command Help:"));
                player.sendMessage(CC.translate(" &f● &b/speed fly &8<&7value&8> &8| &7Set your flying speed."));
                player.sendMessage(CC.translate(" &f● &b/speed walk &8<&7value&8> &8| &7Set your walking speed."));
                player.sendMessage(CC.translate(" &f● &b/speed reset &8| &7Reset your speed"));
                player.sendMessage(CC.FLOWER_BAR_LONG);
                player.sendMessage(" ");
                break;
        }
    }
}
