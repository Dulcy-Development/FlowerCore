package me.emmiesa.flowercore.utils;


import java.util.ArrayList;
import java.util.List;
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

public class Clickable {
    private final List<TextComponent> components = new ArrayList<>();

    public TextComponent add(String msg, String hoverMsg, String clickString) {
        TextComponent message = new TextComponent(msg);
        if (hoverMsg != null) {
            message.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, (new ComponentBuilder(hoverMsg)).create()));
        }

        if (clickString != null) {
            message.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, clickString));
        }

        this.components.add(message);
        return message;
    }

    public void add(String message) {
        this.components.add(new TextComponent(message));
    }

    public void sendToPlayer(Player player) {
        player.spigot().sendMessage(this.asComponents());
    }

    public TextComponent[] asComponents() {
        return this.components.toArray(new TextComponent[0]);
    }
}
