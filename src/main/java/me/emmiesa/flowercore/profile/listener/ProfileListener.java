package me.emmiesa.flowercore.profile.listener;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.profile.Profile;
import me.emmiesa.flowercore.punishment.Punishment;
import me.emmiesa.flowercore.punishment.PunishmentType;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.UUID;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class ProfileListener implements Listener {

    private final FlowerCore plugin = FlowerCore.getInstance();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        UUID playerUUID = event.getUniqueId();

        if (plugin.getProfileRepository() == null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§cError: Player manager not available.");
            return;
        }

        Profile profile = plugin.getProfileRepository().getProfile(playerUUID);
        if (profile == null) {
            plugin.getProfileRepository().setupPlayer(playerUUID);
            profile = plugin.getProfileRepository().getProfile(playerUUID);

            if (profile == null) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§cCould not load your profile. Please try to rejoin.");
                return;
            }
        }

        if (plugin.getRankRepository().getDefaultRank() == null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§cError: Default rank not set.");
            return;
        }

        List<Punishment> punishments = profile.getPunishments();
        if (punishments == null) {
            return;
        }

        for (Punishment punishment : punishments) {
            if (punishment.isActive() && (punishment.getType().equals(PunishmentType.BAN))) {
                String sendBanPunishMessage = banPunishMessage(punishment);
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, sendBanPunishMessage);
                return;
            } else if (punishment.isActive() && (punishment.getType().equals(PunishmentType.BLACKLIST))) {
                String sendBlacklistPunishMessage = blacklistPunishMessage(punishment);
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, sendBlacklistPunishMessage);
                return;
            }
        }
    }

    private String banPunishMessage(Punishment punishment) {
        return CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punishments.ban").replace("%punisher%", punishment.getBy()).replace("%reason%", punishment.getReason()));
    }

    private String blacklistPunishMessage(Punishment punishment) {
        return CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("punishments.blacklist").replace("%punisher%", punishment.getBy()).replace("%reason%", punishment.getReason()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (FlowerCore.getInstance().getRankRepository().getDefaultRank() == null) {
            player.sendMessage(Locale.RANK_NOT_SET);
        }
        plugin.getProfileRepository().addPermissions(playerUUID);
        plugin.getMongoService().initializeProfile(playerUUID);

        if (FlowerCore.getInstance().getConfig("settings.yml").getBoolean("on-join.play-sound.enabled")) {
            String sound = FlowerCore.getInstance().getConfig("settings.yml").getString("on-join.play-sound.sound");
            player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
        }

        if (plugin.getConfig("settings.yml").getBoolean("on-join.clear-chat.enabled")) {
            int linesToClear = plugin.getConfig("settings.yml").getInt("on-join.clear-chat.lines");
            for (int i = 0; i < linesToClear; i++) {
                player.sendMessage(plugin.getConfig("settings.yml").getString("on-join.clear-chat.text"));
            }
        }

        player.setFlySpeed(1 * 0.1F);
        player.setWalkSpeed(2 * 0.1F);

        Location spawnLocation = plugin.getSpawnLocation();
        if (spawnLocation != null) {
            event.getPlayer().teleport(spawnLocation);
        }

        if (plugin.getConfig("messages.yml").getBoolean("on-join.title-sender.enabled")) {
            String mainTitle = FlowerCore.getInstance().getConfig("messages.yml").getString("on-join.title-sender.main-title").replace("%player%", player.getName());
            String subTitle = FlowerCore.getInstance().getConfig("messages.yml").getString("on-join.title-sender.sub-title").replace("%player%", FlowerCore.getInstance().getProfileRepository().getPlayerRankColor(player.getUniqueId()) + player.getName());

            player.sendTitle(CC.translate(mainTitle), CC.translate(subTitle));
        }

        if (plugin.getConfig("messages.yml").getBoolean("on-join.messages.welcome-message.enabled", true)) {
            List<String> welcomeMessages = plugin.getConfig("messages.yml").getStringList("on-join.messages.welcome-message.message");
            sendWelcomeMessages(player, welcomeMessages);
        }

        if (plugin.getConfig("settings.yml").getBoolean("on-join.clear-inventory")) {
            player.getInventory().clear();
        }

        if (plugin.getConfig("settings.yml").getBoolean("on-join.set-gamemode.enabled", true)) {
            String gameMode = plugin.getConfig("settings.yml").getString("on-join.set-gamemode");
            if (gameMode != null) {
                switch (gameMode.toUpperCase()) {
                    case "SURVIVAL":
                        player.setGameMode(GameMode.SURVIVAL);
                        break;
                    case "CREATIVE":
                        player.setGameMode(GameMode.CREATIVE);
                        break;
                    case "ADVENTURE":
                        player.setGameMode(GameMode.ADVENTURE);
                        break;
                    case "SPECTATOR":
                        player.setGameMode(GameMode.SPECTATOR);
                        break;
                    default:
                        break;
                }
            }
        }

        String server = plugin.getConfig("settings.yml").getString("server-name");
        String joinMessage = plugin.getConfig("messages.yml").getString("on-join.messages.staff.joined-the-game");

        if (joinMessage == null || server == null) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&4Warning: Join message or server name is null."));
        } else {
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                if (onlinePlayers.hasPermission("flowercore.staff") && onlinePlayers.hasPermission("flowercore.staff")) {
                    onlinePlayers.sendMessage(CC.translate(joinMessage.replace("%prefix%", plugin.getProfileRepository().getRank(playerUUID).getPrefix()).replace("%player%", onlinePlayers.getDisplayName()).replace("%server%", server)/*.replace("%bars%", plugin.getConfig("messages.yml").getString("on-join.messages.bars-format"))*/));
                    Bukkit.getConsoleSender().sendMessage(CC.translate(joinMessage.replace("%prefix%", plugin.getProfileRepository().getRank(playerUUID).getPrefix()).replace("%player%", onlinePlayers.getDisplayName()).replace("%server%", server)));
                }
            }
        }

        if (player.hasPermission("flowercore.donator.joinmessage")) {
            if (plugin.getConfig("messages.yml").getBoolean("on-join.messages.donator.enabled")) {
                Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("on-join.messages.donator.joined-the-game").replace("%prefix%", FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).getPrefix()).replace("%player%", player.getName())));
                event.setJoinMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("on-join.messages.donator.joined-the-game").replace("%prefix%", FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).getPrefix()).replace("%player%", player.getName())));
            } else {
                event.setJoinMessage(null);
                Bukkit.getConsoleSender().sendMessage(CC.translate("&6[Player Join-logs] " + player.getName() + " joined. &8(" + player.getUniqueId() + "&8)"));
            }
        } else {
            event.setJoinMessage(null);
            Bukkit.getConsoleSender().sendMessage(CC.translate("&6[Player Join-logs] " + player.getName() + " joined. &8(" + player.getUniqueId() + "&8)"));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player disconnectedPlayer = event.getPlayer();
        UUID playerUUID = disconnectedPlayer.getUniqueId();

        String server = plugin.getConfig("settings.yml").getString("server-name");
        String leaveMessage = plugin.getConfig("messages.yml").getString("on-leave.messages.staff.left-the-game");

        if (leaveMessage == null || server == null) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&4Warning: Leave message or server name is null."));
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("flowercore.staff") && disconnectedPlayer.hasPermission("flowercore.staff")) {
                    player.sendMessage(CC.translate(leaveMessage.replace("%prefix%", plugin.getProfileRepository().getRank(playerUUID).getPrefix()).replace("%player%", disconnectedPlayer.getDisplayName()).replace("%server%", server)/*.replace("%bars%", plugin.getConfig("messages.yml").getString("on-join.messages.bars-format"))*/));
                    Bukkit.getConsoleSender().sendMessage(CC.translate(leaveMessage.replace("%prefix%", plugin.getProfileRepository().getRank(playerUUID).getPrefix()).replace("%player%", disconnectedPlayer.getDisplayName()).replace("%server%", server)/*.replace("%bars%", plugin.getConfig("messages.yml").getString("on-join.messages.bars-format"))*/));
                }
            }
        }
        if (disconnectedPlayer.hasPermission("flowercore.donator.joinmessage")) {
            if (plugin.getConfig("messages.yml").getBoolean("on-leave.messages.donator.enabled")) {
                Bukkit.getConsoleSender().sendMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("on-leave.messages.donator.left-the-game").replace("%prefix%", FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).getPrefix()).replace("%player%", disconnectedPlayer.getName())));
                event.setQuitMessage(CC.translate(FlowerCore.getInstance().getConfig("messages.yml").getString("on-leave.messages.donator.left-the-game").replace("%prefix%", FlowerCore.getInstance().getProfileRepository().getRank(playerUUID).getPrefix()).replace("%player%", disconnectedPlayer.getName())));
            } else {
                event.setQuitMessage(null);
                Bukkit.getConsoleSender().sendMessage(CC.translate("&8[Player Leave-logs] " + disconnectedPlayer.getName() + " left. &8(" + disconnectedPlayer.getUniqueId() + "&8)"));
            }
        } else {
            event.setQuitMessage(null);
            Bukkit.getConsoleSender().sendMessage(CC.translate("&8[Player Leave-logs] " + disconnectedPlayer.getName() + " left. &8(" + disconnectedPlayer.getUniqueId() + "&8)"));
        }

        FlowerCore.getInstance().getMongoService().saveProfile(playerUUID);
    }

    private void sendWelcomeMessages(Player player, List<String> messages) {
        for (String message : messages) {
            String replacedMessage = replaceWelcomeMessage(player, message);
            player.sendMessage(CC.translate(replacedMessage));
        }
    }

    private String replaceWelcomeMessage(Player player, String message) {
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
                .replace("%rank%", plugin.getProfileRepository().getRank(playerUUID).getDisplayName());
    }
}