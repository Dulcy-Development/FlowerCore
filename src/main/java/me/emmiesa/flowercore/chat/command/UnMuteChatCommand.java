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
 * @date 04/06/2024 - 20:41
 */
public class UnMuteChatCommand extends BaseCommand {
    @Override
    @Command(name = "unmutechat", permission = "flowercore.command.unmutechat", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        FlowerCore.getInstance().getChatRepository().setChatMuted(false);
        Utils.broadcastMessage(CC.translate("&aChat has been unmuted by &2" + sender.getName() + "&a."));
    }
}