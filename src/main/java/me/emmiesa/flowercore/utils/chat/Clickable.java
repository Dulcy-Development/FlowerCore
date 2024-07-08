package me.emmiesa.flowercore.utils.chat;


import java.util.ArrayList;
import java.util.List;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project FlowerCore
 * @date 21/03/2024 - 21:28
 */
@UtilityClass
public class Clickable {
    private final List<TextComponent> components = new ArrayList<>();

    /**
     * Add a clickable message to the list
     *
     * @param msg the message
     * @param hoverMsg the hover message
     * @param clickString the click string
     * @return the text component
     */
    public TextComponent add(String msg, String hoverMsg, String clickString) {
        TextComponent message = new TextComponent(msg);
        if (hoverMsg != null) {
            message.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, (new ComponentBuilder(hoverMsg)).create()));
        }

        if (clickString != null) {
            message.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, clickString));
        }

        components.add(message);
        return message;
    }

    /**
     * Add a clickable message to the list
     *
     * @param message the message
     */
    public void add(String message) {
        components.add(new TextComponent(message));
    }

    /**
     * Send the clickable message to the player
     *
     * @param player the player
     */
    public void sendToPlayer(Player player) {
        player.spigot().sendMessage(asComponents());
    }

    /**
     * Get the components as an array
     *
     * @return the components
     */
    public TextComponent[] asComponents() {
        return components.toArray(new TextComponent[0]);
    }
}
