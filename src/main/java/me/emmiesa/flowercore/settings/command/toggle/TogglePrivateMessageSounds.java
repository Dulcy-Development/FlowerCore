package me.emmiesa.flowercore.settings.command.toggle;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.settings.PlayerSettings;
import me.emmiesa.flowercore.profile.Profile;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 02/04/2024 - 14:08
 */
public class TogglePrivateMessageSounds extends BaseCommand {
    @Override
    @Command(name = "tpms", aliases = {"toggleprivatemessagesounds"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        UUID playerUUID = player.getUniqueId();

        Profile profile = FlowerCore.getInstance().getProfileRepository().getProfile(playerUUID);
        PlayerSettings playerSettings = profile.getPlayerSettings();

        playerSettings.setMessageSoundsEnabled(!playerSettings.isMessageSoundsEnabled());
        FlowerCore.getInstance().getMongoService().saveProfile(playerUUID);

        String context = playerSettings.isMessageSoundsEnabled() ? CC.translate("&aenabled") : CC.translate("&cdisabled");
        player.sendMessage(CC.translate("&bYou've {context} &byour message sounds.").replace("{context}", context));
    }
}