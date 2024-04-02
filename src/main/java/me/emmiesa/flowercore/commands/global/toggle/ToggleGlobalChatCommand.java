package me.emmiesa.flowercore.commands.global.toggle;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.profile.Profile;
import me.emmiesa.flowercore.playersettings.PlayerSettingsManager;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Date: 12/03/2024 - 17:10
 */

public class ToggleGlobalChatCommand extends BaseCommand {
    @Override
    @Command(name = "tgc", aliases = "toggleglobalchat")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        UUID playerUUID = player.getUniqueId();

        Profile profile = FlowerCore.getInstance().getPlayerManager().getProfile(playerUUID);
        PlayerSettingsManager playerSettingsManager = profile.getPlayerSettingsManager();

        playerSettingsManager.setGlobalChatEnabled(!playerSettingsManager.isGlobalChatEnabled());
        FlowerCore.getInstance().getMongoManager().saveProfile(playerUUID);

        String context = playerSettingsManager.isGlobalChatEnabled() ? CC.translate("&aenabled") : CC.translate("&cdisabled");
        player.sendMessage(CC.translate("&bYou've {context} your global chat.").replace("{context}", context));
    }
}
