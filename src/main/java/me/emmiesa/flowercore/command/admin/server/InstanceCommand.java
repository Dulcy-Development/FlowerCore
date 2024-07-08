package me.emmiesa.flowercore.command.admin.server;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.locale.Locale;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.api.command.BaseCommand;
import me.emmiesa.flowercore.api.command.annotation.Command;
import me.emmiesa.flowercore.api.command.CommandArgs;
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

        StringBuilder pluginsList = new StringBuilder();
        String separator = FlowerCore.getInstance().getConfig("messages.yml").getString("instance-command.seperator-format", ", ");
        for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
            if (pluginsList.length() > 0) {
                pluginsList.append(CC.translate(separator));
            }
            pluginsList.append(plugin.getName());
        }

        int opCount = 0;
        List<String> opNames = new ArrayList<>();
        for (OfflinePlayer offlinePlayer : Bukkit.getServer().getOfflinePlayers()) {
            if (offlinePlayer.isOp()) {
                opCount++;
                opNames.add(offlinePlayer.getName());
            }
        }

        int pluginCount = Bukkit.getServer().getPluginManager().getPlugins().length;

        for (String message : FlowerCore.getInstance().getConfig("messages.yml").getStringList("instance-command.lines")) {
            sender.sendMessage(CC.translate(message)
                    .replace("%server-region%", Locale.SERVER_REGION)
                    .replace("%server-name%", Locale.SERVER_NAME)
                    .replace("%bukkit-server-name%", Bukkit.getServerName())
                    .replace("%version%", Bukkit.getServer().getVersion())
                    .replace("%spigot%", Bukkit.getServer().getName())
                    .replace("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                    .replace("%online-players%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                    .replace("%plugins-name%", pluginsList.toString())
                    .replace("%plugins%", String.valueOf(pluginCount))
                    .replace("%ops%", String.valueOf(opCount))
                    .replace("%ops-name%", String.join(CC.translate(separator), opNames)));
        }
    }
}
