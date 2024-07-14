package me.emmy.core.essential.godmode.command;

import me.emmy.core.FlowerCore;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.CommandArgs;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.essential.godmode.GodModeMemory;
import me.emmy.core.utils.chat.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 14/07/2024 - 15:08
 */
public class GodModeCommand extends BaseCommand {
    @Override
    @Command(name = "godmode", permission = "flower.command.godmode")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String [] args = command.getArgs();

        GodModeMemory godModeMemory = FlowerCore.getInstance().getGodModeMemory();

        if (args.length < 1) {
            if (!godModeMemory.isGodModeEnabled(player)) {
                godModeMemory.enableGodMode(player);
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("god-mode.enabled")));
            } else {
                godModeMemory.disableGodMode(player);
                player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("god-mode.disabled")));
            }

            return;
        }

        Player targetPlayer = player.getServer().getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return;
        }

        if (!godModeMemory.isGodModeEnabled(targetPlayer)) {
            godModeMemory.enableGodMode(targetPlayer);
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("god-mode.target.enabled").replace("%player%", targetPlayer.getName())));
            targetPlayer.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("god-mode.enabled")));
        } else {
            godModeMemory.disableGodMode(targetPlayer);
            player.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("god-mode.target.disabled").replace("%player%", targetPlayer.getName())));
            targetPlayer.sendMessage(CC.translate(FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("god-mode.disabled")));
        }

    }
}
