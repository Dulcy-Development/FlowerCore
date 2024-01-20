package me.emmiesa.flowercore.listeners;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.UUID;

public class PlayerListeners implements Listener {

    private final FlowerCore plugin;

    public PlayerListeners(FlowerCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        Player joinedPlayer = event.getPlayer();
        UUID playerUUID = joinedPlayer.getUniqueId();

        if (plugin.getRanksManager().getDefaultRank() != null) {
            plugin.getPlayerManager().setupPlayer(playerUUID);
        } else {
            joinedPlayer.sendMessage(CC.translate("&cDefault rank isn't set!"));
        }

        if (plugin.getConfig("messages.yml").getBoolean("on-join.messages.welcome-message.enabled")) {
            List<String> welcomeMessages = plugin.getConfig("messages.yml").getStringList("on-join.messages.welcome-message.message");
            sendWelcomeMessages(joinedPlayer, welcomeMessages);
        }


        if (plugin.getConfig("settings.yml").getBoolean("on-join.clear-inventory")) {
            joinedPlayer.getInventory().clear();
        }

        if (plugin.getConfig("settings.yml").getBoolean("on-join.set-gamemode.enabled")) {
            String gamemode = plugin.getConfig("settings.yml").getString("on-join.set-gamemode");
            if (gamemode != null) {
                switch (gamemode.toUpperCase()) {
                    case "SURVIVAL":
                        joinedPlayer.setGameMode(GameMode.SURVIVAL);
                        break;
                    case "CREATIVE":
                        joinedPlayer.setGameMode(GameMode.CREATIVE);
                        break;
                    case "ADVENTURE":
                        joinedPlayer.setGameMode(GameMode.ADVENTURE);
                        break;
                    case "SPECTATOR":
                        joinedPlayer.setGameMode(GameMode.SPECTATOR);
                        break;
                    default:
                        break;
                }
            }
        }

        String server = plugin.getConfig("settings.yml").getString("server-name");
        String joinMessage = plugin.getConfig("messages.yml").getString("on-join.messages.staff.joined-the-game");

        if (joinMessage != null && server != null) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("flowercore.staff")) {
                    onlinePlayer.sendMessage(CC.translate(joinMessage.replace("%player%", joinedPlayer.getName()).replace("%server%", server)/*.replace("%bars%", plugin.getConfig("messages.yml").getString("on-join.messages.bars-format"))*/));
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&4Warning: Join message or server name is null."));
        }
    }

    private void sendWelcomeMessages(Player player, List<String> messages) {
        for (String message : messages) {
            String replacedMessage = replacewelcomemessage(player, message);
            player.sendMessage(CC.translate(replacedMessage));
        }
    }

    private String replacewelcomemessage(Player player, String message) {

        String website = plugin.getConfig("settings.yml").getString("socials.website");
        String store = plugin.getConfig("settings.yml").getString("socials.store");
        String discord = plugin.getConfig("settings.yml").getString("socials.discord");
        String teamspeak = plugin.getConfig("settings.yml").getString("socials.teamspeak");
        String tiktok = plugin.getConfig("settings.yml").getString("socials.tiktok");
        String twitter = plugin.getConfig("settings.yml").getString("socials.twitter");
        String youtube = plugin.getConfig("settings.yml").getString("socials.youtube");
        String bars = plugin.getConfig("messages.yml").getString("on-join.messages.bars-format");

        String replacedMessage = message
                .replace("%player%", player.getName())
                .replace("%health%", String.valueOf(player.getHealth()))
                .replace("%bars%", bars)
                .replace("%website%", website)
                .replace("%store%", store)
                .replace("%discord%", discord)
                .replace("%teamspeak%", teamspeak)
                .replace("%tiktok%", tiktok)
                .replace("%twitter%", twitter)
                .replace("%youtube%", youtube)
                ;

        return replacedMessage;
    }
}