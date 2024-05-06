package me.emmiesa.flowercore.commands.admin.essential;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.command.BaseCommand;
import me.emmiesa.flowercore.utils.command.Command;
import me.emmiesa.flowercore.utils.command.CommandArgs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Emmy
 * Project: FlowerCore
 * Discord: dsc.gg/emmiesa
 */

public class GodModeCommand extends BaseCommand implements Listener {

    private final Set<Player> godModePlayers = new HashSet<>();

    public GodModeCommand(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Command(name = "godmode", aliases = {"god", "togglegodmode"}, permission = "flowercore.command.god")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (godModePlayers.contains(player)) {
            disableGodMode(player);
            player.setAllowFlight(false);
            player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("god-mode.disabled")));
        } else {
            enableGodMode(player);
            player.setAllowFlight(true);
            player.sendMessage(CC.translate(FlowerCore.getINSTANCE().getConfig("messages.yml").getString("god-mode.enabled")));
        }
    }

    private void enableGodMode(Player player) {
        godModePlayers.add(player);
    }

    private void disableGodMode(Player player) {
        godModePlayers.remove(player);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (godModePlayers.contains(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (godModePlayers.contains(player)) {
                event.setCancelled(true);
                player.setFoodLevel(20);
            }
        }
    }
}