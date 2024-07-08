package me.emmiesa.flowercore.chat.command;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.Utils;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 04/06/2024 - 20:28
 */
public class MuteChatCommand extends BaseCommand {
    @Override
    @Command(name = "mutechat", permission = "flowercore.command.mutechat", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        FlowerCore.getInstance().getChatRepository().setChatMuted(true);
        Utils.broadcastMessage(CC.translate("&cChat has been muted by &4" + sender.getName() + "&c."));
    }
}