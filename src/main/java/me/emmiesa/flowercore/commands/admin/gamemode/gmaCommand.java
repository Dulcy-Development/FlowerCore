package me.emmiesa.flowercore.commands.admin.gamemode;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class gmaCommand extends BaseCommand {

    @Command(name = "gma", aliases = {"gm.a", "adventure", "gamemode.a","gamemode.2", "gm.2", "gm2"}, permission = "flowercore.staff")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        if (player.getGameMode().equals(GameMode.ADVENTURE)) {
            player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("gamemode.adventure.is_already")));
            return;
        }

        player.sendMessage(CC.translate(FlowerCore.instance.getConfig("messages.yml").getString("gamemode.adventure.switched")));
        player.setGameMode(GameMode.ADVENTURE);

    }
}
