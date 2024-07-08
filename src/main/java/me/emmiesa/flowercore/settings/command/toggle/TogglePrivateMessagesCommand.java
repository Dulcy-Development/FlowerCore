package me.emmiesa.flowercore.settings.command.toggle;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.profile.Profile;
import me.emmiesa.flowercore.settings.PlayerSettings;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 02/04/2024 - 12:10
 */
public class TogglePrivateMessagesCommand extends BaseCommand {
    @Override
    @Command(name = "tpm", aliases = "toggleprivatemessages")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        UUID playerUUID = player.getUniqueId();

        Profile profile = FlowerCore.getInstance().getProfileRepository().getProfile(playerUUID);
        PlayerSettings playerSettings = profile.getPlayerSettings();

        playerSettings.setPrivateMessagesEnabled(!playerSettings.isPrivateMessagesEnabled());
        FlowerCore.getInstance().getMongoService().saveProfile(playerUUID);

        String context = playerSettings.isPrivateMessagesEnabled() ? CC.translate("&aenabled") : CC.translate("&cdisabled");
        player.sendMessage(CC.translate("&bYou've {context} your private messages.").replace("{context}", context));
    }
}
