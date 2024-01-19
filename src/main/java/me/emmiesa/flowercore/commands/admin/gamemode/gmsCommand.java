package me.emmiesa.flowercore.commands.admin.gamemode;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class gmsCommand extends BaseCommand {

    @Command(name = "gms", aliases = {"gm.s", "survival", "gamemode.s","gamemode.0", "gm.0", "gm0"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("gamemode.survival.is_already")));
            return;
        }

        player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("gamemode.survival.switched")));
        player.setGameMode(GameMode.SURVIVAL);

    }
}
