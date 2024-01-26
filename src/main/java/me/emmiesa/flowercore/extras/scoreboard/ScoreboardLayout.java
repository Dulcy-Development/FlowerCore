package me.emmiesa.flowercore.extras.scoreboard;

import me.emmiesa.flowercore.Lang;
import me.emmiesa.flowercore.extras.scoreboard.assemble.AssembleAdapter;
import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardLayout implements AssembleAdapter {

	private final FlowerCore plugin = FlowerCore.get();

	@Override
	public String getTitle(Player player) {
		return CC.translate(plugin.getConfig("extras.yml").getString("scoreboard.title").replace("%server-name%", Lang.SERVER_NAME));
	}

	@Override
	public List<String> getLines(Player player) {
		List<String> lines = new ArrayList<>();

		List<String> originalLines = plugin.getConfig("extras.yml").getStringList("scoreboard.lines");
		for (String line : originalLines) {
			line = line.replace("%online-players%", String.valueOf(Bukkit.getOnlinePlayers().size()))
					.replace("%server-name%", Lang.SERVER_NAME)
					.replace("%server-region%", Lang.SERVER_REGION)
					.replace("%username%", player.getName());
			lines.add(CC.translate(line));
		}

		return lines;
	}
}
