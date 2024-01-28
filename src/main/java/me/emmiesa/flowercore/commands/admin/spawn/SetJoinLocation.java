package me.emmiesa.flowercore.commands.admin.spawn;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class SetJoinLocation extends BaseCommand {

    @Command(name = "setjoinlocation", permission = "flowercore.command.tphere", aliases = "setspawnlocation")

    @Override
    public void onCommand(CommandArgs cmd) {
        Player player = (Player) cmd.getSender();
        FlowerCore.instance.setSpawnLocation(player.getLocation());
        player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("spawn-set")));
    }
}