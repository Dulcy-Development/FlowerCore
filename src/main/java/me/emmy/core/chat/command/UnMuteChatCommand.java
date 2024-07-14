package me.emmy.core.chat.command;

import me.emmy.core.FlowerCore;
import me.emmy.core.utils.Utils;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
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