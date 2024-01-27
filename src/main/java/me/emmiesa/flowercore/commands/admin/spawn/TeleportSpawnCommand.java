package me.emmiesa.flowercore.commands.admin.spawn;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class TeleportSpawnCommand extends BaseCommand {
    @Command(name = "teleportspawn", permission = "flowercore.staff", aliases = {"tpspawn", "tpjoinlocation", "tplobby"})
    @Override
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (FlowerCore.instance.getConfig().getBoolean("on-join.teleport.enabled")) {
            FlowerCore.instance.teleportToSpawn(player);
            player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("teleport.tp-spawn")));
        } else {
            player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("spawn.not-set.reminder.message")));
        }
    }
}