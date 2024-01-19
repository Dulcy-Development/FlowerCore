package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class HealCommand extends BaseCommand {

    @Command(name = "heal", aliases = "sethealthfull", permission = "flowercore.command.heal")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() == 0) {
            healPlayer(player);
            player.sendMessage(CC.translate("&fYou've been &bhealed."));
        } else if (args.length() == 1) {
            Player targetPlayer = player.getServer().getPlayer(args.getArgs()[0]);
            if (targetPlayer != null && targetPlayer.isOnline()) {
                healPlayer(targetPlayer);
                player.sendMessage(CC.translate("&b" + targetPlayer.getName() + " &fhas been healed."));
            } else {
                player.sendMessage(CC.translate("&fNo player matching that name &bis &fconnected to this server."));
            }
        } else {
            player.sendMessage("Usage: /heal [player]");
        }
    }

    private void healPlayer(Player player) {
        player.setHealth(player.getMaxHealth());
    }
}