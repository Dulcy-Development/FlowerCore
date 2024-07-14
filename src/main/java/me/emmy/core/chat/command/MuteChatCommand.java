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