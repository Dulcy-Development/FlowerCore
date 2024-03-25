/*package me.emmiesa.flowercore.nametag;

import me.emmiesa.flowercore.FlowerCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;


public class NametagManager {

    public static void updatePlayerNametag(Player player) {

        if (!FlowerCore.getInstance().getConfig("settings.yml").getBoolean("nametags.enabled")) {
            resetPlayerNametag(player);
            return;
        }

        String rankColor = FlowerCore.getInstance().getPlayerManager().getPlayerRankColor(player.getUniqueId());
        String translatedColor = ChatColor.translateAlternateColorCodes('&', rankColor);
        //String translatedColor = CC.translate(rankColor);

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) return;

        Scoreboard scoreboard = player.getScoreboard();
        String teamName = "rank" + translatedColor.charAt(1);
        Team team = scoreboard.getTeam(teamName);

        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }

        team.setPrefix(translatedColor);
        team.addEntry(player.getName());
    }

    public static void resetPlayerNametag(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Team team = scoreboard.getEntryTeam(player.getName());
        if (team != null) {
            team.removeEntry(player.getName());
        }
    }
}
*/