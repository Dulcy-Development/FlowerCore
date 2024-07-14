package me.emmy.core.command.admin.server;

import me.emmy.core.FlowerCore;
import me.emmy.core.locale.Locale;
import me.emmy.core.utils.chat.CC;
import me.emmy.core.api.command.BaseCommand;
import me.emmy.core.api.command.annotation.Command;
import me.emmy.core.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project FlowerCore
 * @date -
 */
public class InstanceCommand extends BaseCommand {
    @Override
    @Command(name = "instance", aliases = "serverdetails", permission = "flower.command.instance", inGameOnly = false)
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        StringBuilder stringBuilder = new StringBuilder();
        String separator = FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getString("instance-command.seperator-format", ", ");
        for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(CC.translate(separator));
            }
            stringBuilder.append(plugin.getName());
        }

        int opCount = 0;
        List<String> opPlayers = new ArrayList<>();
        for (OfflinePlayer offlinePlayer : Bukkit.getServer().getOfflinePlayers()) {
            if (offlinePlayer.isOp()) {
                opCount++;
                opPlayers.add(offlinePlayer.getName());
            }
        }

        int pluginCount = Bukkit.getServer().getPluginManager().getPlugins().length;

        for (String message : FlowerCore.getInstance().getConfigHandler().getConfig("messages.yml").getStringList("instance-command.lines")) {
            sender.sendMessage(CC.translate(message)
                    .replace("%server-region%", Locale.SERVER_REGION)
                    .replace("%server-name%", Locale.SERVER_NAME)
                    .replace("%bukkit-server-name%", Bukkit.getServerName())
                    .replace("%version%", Bukkit.getServer().getVersion())
                    .replace("%spigot%", Bukkit.getServer().getName())
                    .replace("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                    .replace("%online-players%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                    .replace("%plugins-name%", stringBuilder.toString())
                    .replace("%plugins%", String.valueOf(pluginCount))
                    .replace("%ops%", String.valueOf(opCount))
                    .replace("%ops-name%", String.join(CC.translate(separator), opPlayers)));
        }
    }
}
