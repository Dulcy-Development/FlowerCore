package me.emmiesa.flowercore.command.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import me.emmiesa.flowercore.utils.command.Completer;
import me.emmiesa.flowercore.utils.NumberUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class SpeedCommand extends BaseCommand {
    @Completer(name = "speed")
    public List<String> speedCompleter(CommandArgs args) {
        List<String> commands = new ArrayList<>();
        if (args.getPlayer().hasPermission("flower.command.speed") && args.length() == 1) {
            commands.add("fly");
            commands.add("walk");
            commands.add("reset");
        }
        return commands;
    }

    @Override
    @Command(name = "speed", permission = "flower.command.speed")
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
                if (NumberUtils.checkInt(args[1])) {
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
                    player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("speed-set.fly").replace("%value%", String.valueOf(speedVelocity))));
                } else {
                    player.setWalkSpeed(speedVelocity * 0.1F);
                    player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("speed-set.walk").replace("%value%", String.valueOf(speedVelocity))));
                }
                break;
            case "reset":
                player.setFlySpeed(0.1F);
                player.setWalkSpeed(0.2F);
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("speed-set.reset")));
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
