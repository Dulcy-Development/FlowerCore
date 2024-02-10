package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class SpeedResetCommand extends BaseCommand {
    @Command(name = "defaultspeed", aliases = {"speedreset", "resetspeed"}, permission = "flowercore.cmd.speed")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        player.setFlySpeed(0.1F);
        player.setWalkSpeed(0.2F);
        player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("speed-set.reset")));
    }
}
