package me.emmiesa.flowercore.commands.admin.gamemode;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class gmcCommand extends BaseCommand {

    @Command(name = "gmc", aliases = {"gm.c", "creative", "gamemode.c", "gamemode.1", "gm.1", "gm1"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("gamemode.creative.is_already")));
            return;
        }

        player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("gamemode.creative.switched")));
        player.setGameMode(GameMode.CREATIVE);

    }
}
