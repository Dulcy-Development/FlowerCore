package me.emmiesa.flowercore.commands.admin.gamemode;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class gmspCommand extends BaseCommand {

    @Command(name = "gmsp", aliases = {"gm.sp", "spectator", "gamemode.sp","gamemode.3", "gm.3", "gm3"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("gamemode.spectator.is_already")));
            return;
        }

        player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("gamemode.spectator.switched")));
        player.setGameMode(GameMode.SPECTATOR);

    }
}
