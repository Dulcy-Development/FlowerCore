package me.emmiesa.flowercore.commands.admin.punishments;

import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class UnbanCommand extends BaseCommand {
    @Override
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 1) {
            player.sendMessage(CC.translate("&cUsage: /unban (player) (reason) [-s]"));
            return;
        }
    }
}
