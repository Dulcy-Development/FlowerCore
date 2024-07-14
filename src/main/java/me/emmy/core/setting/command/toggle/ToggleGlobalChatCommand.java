package me.emmy.core.setting.command.toggle;

import me.emmy.core.FlowerCore;
import me.emmy.core.profile.Profile;
import me.emmy.core.setting.PlayerSettings;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 12/03/2024 - 17:10
 */
public class ToggleGlobalChatCommand extends BaseCommand {
    @Override
    @Command(name = "tgc", aliases = "toggleglobalchat")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        UUID playerUUID = player.getUniqueId();

        Profile profile = FlowerCore.getInstance().getProfileRepository().getProfile(playerUUID);
        PlayerSettings playerSettings = profile.getPlayerSettings();

        playerSettings.setGlobalChatEnabled(!playerSettings.isGlobalChatEnabled());
        FlowerCore.getInstance().getMongoService().saveProfile(playerUUID);

        String context = playerSettings.isGlobalChatEnabled() ? CC.translate("&aenabled") : CC.translate("&cdisabled");
        player.sendMessage(CC.translate("&bYou've {context} your global chat.").replace("{context}", context));
    }
}
