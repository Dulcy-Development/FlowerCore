package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class HealCommand extends BaseCommand {

    @Command(name = "heal", aliases = "sethealthfull", permission = "flowercore.command.heal")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() == 0) {
            healPlayer(player);
            player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("heal.healed")));
        } else if (args.length() == 1) {
            Player targetPlayer = player.getServer().getPlayer(args.getArgs()[0]); //Bukkit.getPlayer(command.getArgs()[0]);
            if (targetPlayer != null && targetPlayer.isOnline()) {
                healPlayer(targetPlayer);
                player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("heal.target-healed").replace("%target%", targetPlayer.getName())));
            } else {
                player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("heal.target-not-found").replace("%target%", args.getArgs()[0])));
            }
        } else {
            player.sendMessage(CC.translate("Usage: /heal (player)"));
        }
    }

    private void healPlayer(Player player) {
        player.setHealth(player.getMaxHealth());
    }
}