package me.emmiesa.flowercore.listeners;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.Locale;
import me.emmiesa.flowercore.punishments.Punishment;
import me.emmiesa.flowercore.punishments.PunishmentType;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

        if (FlowerCore.instance.getRanksManager().getDefaultRank() != null) {
            FlowerCore.instance.getPlayerManager().setupPlayer(joinedPlayer.getUniqueId());

            for (Punishment punishment : FlowerCore.instance.getPlayerManager().getProfile(joinedPlayer.getUniqueId()).getPunishments()) {
                if (punishment.getType().equals(PunishmentType.BAN) || punishment.getType().equals(PunishmentType.BLACKLIST)) {

                    String punishmessage = CC.translate("&4You've been suspended! \n" + "&cReason: &f" + punishment.getReason() + "\n &cSuspended by: &f" + punishment.getBy() + "\n&cPunishment Type: &f" + punishment.getType());

                    joinedPlayer.kickPlayer(punishmessage);
                }
            }
        }

        if (FlowerCore.instance.getConfig("settings.yml").getBoolean("on-join.play-sound.enabled")) {
            String sound = FlowerCore.instance.getConfig("settings.yml").getString("on-join.play-sound.sound");
            joinedPlayer.playSound(joinedPlayer.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
        }

        if (plugin.getConfig("settings.yml").getBoolean("on-join.clear-chat.enabled")) {
            int linesToClear = plugin.getConfig("settings.yml").getInt("on-join.clear-chat.lines");
            for (int i = 0; i < linesToClear; i++) {
                joinedPlayer.sendMessage(plugin.getConfig("settings.yml").getString("on-join.clear-chat.text"));
            }
        }

        joinedPlayer.setFlySpeed(1 * 0.1F);
        joinedPlayer.setWalkSpeed(2 * 0.1F);

        Location spawnLocation = plugin.getSpawnLocation();

        if (spawnLocation != null) {
            event.getPlayer().teleport(spawnLocation);
        }

        if (plugin.getRanksManager().getDefaultRank() == null) {
            joinedPlayer.sendMessage(CC.translate(Locale.RANK_NOT_SET));
        }

        if (plugin.getConfig("messages.yml").getBoolean("on-join.messages.welcome-message.enabled", true)) {
            List<String> welcomeMessages = plugin.getConfig("messages.yml").getStringList("on-join.messages.welcome-message.message");
            sendWelcomeMessages(joinedPlayer, welcomeMessages);
        }


        if (plugin.getConfig("settings.yml").getBoolean("on-join.clear-inventory")) {
            joinedPlayer.getInventory().clear();
        }

        if (plugin.getConfig("settings.yml").getBoolean("on-join.set-gamemode.enabled", true)) {
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
                if (onlinePlayer.hasPermission("flowercore.staff") && joinedPlayer.hasPermission("flowercore.staff")) {
                    onlinePlayer.sendMessage(CC.translate(joinMessage.replace("%prefix%", plugin.getPlayerManager().getRank(playerUUID).getPrefix()).replace("%player%", joinedPlayer.getDisplayName()).replace("%server%", server)/*.replace("%bars%", plugin.getConfig("messages.yml").getString("on-join.messages.bars-format"))*/));
                    Bukkit.getConsoleSender().sendMessage(CC.translate(joinMessage.replace("%prefix%", plugin.getPlayerManager().getRank(playerUUID).getPrefix()).replace("%player%", joinedPlayer.getDisplayName()).replace("%server%", server)));
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&4Warning: Join message or server name is null."));
        }

        if (joinedPlayer.hasPermission("flowercore.donator.joinmessage")) {
            //event.setJoinMessage(CC.translate("&7» " + FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix() + joinedPlayer.getName() + "&f joined the server!"));
            Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("on-join.messages.donator.joined-the-game").replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix()).replace("%player%", joinedPlayer.getName())));
            event.setJoinMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("on-join.messages.donator.joined-the-game").replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix()).replace("%player%", joinedPlayer.getName())));
        } else {
            event.setJoinMessage(null);
            Bukkit.getConsoleSender().sendMessage(CC.translate("&6[Player Join-logs] " + joinedPlayer.getName() + " joined. &8(" + joinedPlayer.getUniqueId() + "&8)"));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player disconnectedPlayer = event.getPlayer();
        UUID playerUUID = disconnectedPlayer.getUniqueId();

        String server = plugin.getConfig("settings.yml").getString("server-name");
        String leaveMessage = plugin.getConfig("messages.yml").getString("on-leave.messages.staff.left-the-game");


        if (leaveMessage != null && server != null) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("flowercore.staff") && disconnectedPlayer.hasPermission("flowercore.staff")) {
                    onlinePlayer.sendMessage(CC.translate(leaveMessage.replace("%prefix%", plugin.getPlayerManager().getRank(playerUUID).getPrefix()).replace("%player%", disconnectedPlayer.getDisplayName()).replace("%server%", server)/*.replace("%bars%", plugin.getConfig("messages.yml").getString("on-join.messages.bars-format"))*/));
                    Bukkit.getConsoleSender().sendMessage(CC.translate(leaveMessage.replace("%prefix%", plugin.getPlayerManager().getRank(playerUUID).getPrefix()).replace("%player%", disconnectedPlayer.getDisplayName()).replace("%server%", server)/*.replace("%bars%", plugin.getConfig("messages.yml").getString("on-join.messages.bars-format"))*/));
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&4Warning: Leave message or server name is null."));
        }

        if (disconnectedPlayer.hasPermission("flowercore.donator.joinmessage")) {
            //event.setQuitMessage(CC.translate("&7» " + FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix() + disconnectedPlayer.getName() + " &fleft the server!"));
            Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("on-leave.messages.donator.left-the-game").replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix()).replace("%player%", disconnectedPlayer.getName())));
            event.setQuitMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("on-leave.messages.donator.left-the-game").replace("%prefix%", FlowerCore.getInstance().getPlayerManager().getRank(playerUUID).getPrefix()).replace("%player%", disconnectedPlayer.getName())));
        } else {
            event.setQuitMessage(null);
            Bukkit.getConsoleSender().sendMessage(CC.translate("&8[Player Leave-logs] " + disconnectedPlayer.getName() + " left. &8(" + disconnectedPlayer.getUniqueId() + "&8)"));
        }

        FlowerCore.getInstance().getMongoManager().saveProfile(playerUUID);
    }

    private void sendWelcomeMessages(Player player, List<String> messages) {
        for (String message : messages) {
            String replacedMessage = replacewelcomemessage(player, message);
            player.sendMessage(CC.translate(replacedMessage));
        }
    }

    private String replacewelcomemessage(Player player, String message) {
        UUID playerUUID = player.getUniqueId();

        String website = plugin.getConfig("settings.yml").getString("socials.website");
        String store = plugin.getConfig("settings.yml").getString("socials.store");
        String discord = plugin.getConfig("settings.yml").getString("socials.discord");
        String teamspeak = plugin.getConfig("settings.yml").getString("socials.teamspeak");
        String tiktok = plugin.getConfig("settings.yml").getString("socials.tiktok");
        String twitter = plugin.getConfig("settings.yml").getString("socials.twitter");
        String youtube = plugin.getConfig("settings.yml").getString("socials.youtube");

        String bars = plugin.getConfig("messages.yml").getString("on-join.messages.bars-format");

        return message
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
                .replace("%flowerbar%", CC.FLOWER_BAR_LONG)
                .replace("%rank%", plugin.getPlayerManager().getRank(playerUUID).getDisplayName());
    }
}