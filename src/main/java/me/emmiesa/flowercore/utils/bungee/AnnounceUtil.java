package me.emmiesa.flowercore.utils.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.experimental.UtilityClass;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.chat.JSONMessage;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 08/07/2024 - 23:18
 */
@UtilityClass
public class AnnounceUtil {

    /**
     * Sends a global clickable message to all players
     *
     * @param player  The player to send the message to
     * @param message The message to send
     * @param command The command to run when the message is clicked
     */
    public void sendGlobalClickableMessage(Player player, String message, String command) {
        ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
        dataOutput.writeUTF("MessageRaw");
        dataOutput.writeUTF("ALL");
        dataOutput.writeUTF(JSONMessage.create(CC.translate(message))
                .tooltip(CC.translate(FlowerCore.getInstance().getConfig("settings.yml").getString("announce.hover")))
                .runCommand("/" + command).toString());

        if (player != null) {
            player.sendPluginMessage(FlowerCore.getInstance(), "BungeeCord", dataOutput.toByteArray());
        }
    }
}
