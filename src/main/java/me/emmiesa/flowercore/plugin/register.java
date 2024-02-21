package me.emmiesa.flowercore.plugin;

import me.emmiesa.flowercore.FlowerCore;
import me.emmiesa.flowercore.commands.admin.administration.AlertCommand;
import me.emmiesa.flowercore.commands.admin.administration.BroadcastCommand;
import me.emmiesa.flowercore.commands.admin.administration.ClearChatCommand;
import me.emmiesa.flowercore.commands.admin.administration.InstanceCommand;
import me.emmiesa.flowercore.commands.admin.essential.*;
import me.emmiesa.flowercore.commands.admin.gamemode.gmaCommand;
import me.emmiesa.flowercore.commands.admin.gamemode.gmcCommand;
import me.emmiesa.flowercore.commands.admin.gamemode.gmsCommand;
import me.emmiesa.flowercore.commands.admin.gamemode.gmspCommand;
import me.emmiesa.flowercore.commands.admin.punishments.BanCommand;
import me.emmiesa.flowercore.commands.admin.rank.GrantCommand;
import me.emmiesa.flowercore.commands.admin.rank.RankCommand;
import me.emmiesa.flowercore.commands.admin.rank.SubCommands.*;
import me.emmiesa.flowercore.commands.admin.spawn.SetJoinLocation;
import me.emmiesa.flowercore.commands.admin.spawn.TeleportSpawnCommand;
import me.emmiesa.flowercore.commands.admin.tags.TagCommand;
import me.emmiesa.flowercore.commands.admin.teleport.TeleportCommand;
import me.emmiesa.flowercore.commands.admin.teleport.TeleportHereCommand;
import me.emmiesa.flowercore.commands.admin.teleport.TeleportPositionCommand;
import me.emmiesa.flowercore.commands.admin.teleport.TeleportUpCommand;
import me.emmiesa.flowercore.commands.admin.troll.TrollCommand;
import me.emmiesa.flowercore.commands.admin.troll.TrollEverybodyCommand;
import me.emmiesa.flowercore.commands.admin.troll.TrollSilentCommand;
import me.emmiesa.flowercore.commands.donator.AnnounceCommand;
import me.emmiesa.flowercore.commands.global.*;
import me.emmiesa.flowercore.listeners.CommandListener;
import me.emmiesa.flowercore.utils.chat.CC;
import me.emmiesa.flowercore.utils.menu.MenuListener;
import org.bukkit.Bukkit;

public class register {

    public static void check() {
        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] Loading..."));
        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] Checking plugin.yml..."));
        if (!FlowerCore.getInstance().getDescription().getAuthors().contains("Emmiesa") || !FlowerCore.getInstance().getDescription().getName().contains("FlowerCore")) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&4[FlowerCore] WRONG AUTHOR/PLUGIN NAME! Shutting the server down..."));
            System.exit(0);
            Bukkit.shutdown();
        } else {
            Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] No changes detected"));
        }
        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] Started register process..."));
    }

    public static void commands() {
        long start = System.currentTimeMillis();

        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] | Registering all commands..."));

        new FlowerCoreCommand();

        new FlyCommand();
        new gmcCommand();
        new gmsCommand();
        new gmaCommand();
        new TagCommand();
        new BanCommand();
        new gmspCommand();
        new HealCommand();
        new FeedCommand();
        new PingCommand();
        new MoreCommand();
        new TrollCommand();
        new StoreCommand();
        new SpeedCommand();
        new CraftCommand();
        new TikTokCommand();
        new YouTubeCommand();
        new DiscordCommand();
        new TwitterCommand();
        new WebsiteCommand();
        new SettingsCommand();
        new ForceFlyCommand();
        new TeleportCommand();
        new AnnounceCommand();
        new InstanceCommand();
        new TeleportCommand();
        new SetJoinLocation();
        new ClearChatCommand();
        new TeamSpeakCommand();
        new TeleportUpCommand();
        new TrollSilentCommand();
        new TeleportHereCommand();
        new TeleportSpawnCommand();
        new TrollEverybodyCommand();
        new TeleportPositionCommand();

        new RankCommand();
        new GrantCommand();
        new RankListCommand();
        new RankSaveCommand();
        new RankCreateCommand();
        new RankSetIconCommand();
        new RankSetStaffCommand();
        new RankSetSuffixCommand();
        new RankSetPrefixCommand();
        new RankSetDisplayCommand();
        new RankSetDefaultCommand();
        new RankSetPriorityCommand();
        new RankAddPermissionsCommand();

        new AlertCommand(FlowerCore.getInstance());
        new GodModeCommand(FlowerCore.getInstance());
        new BroadcastCommand(FlowerCore.getInstance());

        if (FlowerCore.getInstance().getConfig("commands.yml").getBoolean("plugin.enabled")) {
            new PluginCommand();
        }

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] | Registered all commands in " + timeTaken + "ms."));
    }

    public static void listeners() {
        long start = System.currentTimeMillis();

        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] | Registering all listeners..."));

        FlowerCore.getInstance().getServer().getPluginManager().registerEvents(new MenuListener(), FlowerCore.getInstance());
        FlowerCore.getInstance().getServer().getPluginManager().registerEvents(new me.emmiesa.flowercore.listeners.PlayerListeners(FlowerCore.getInstance()), FlowerCore.getInstance());
        FlowerCore.getInstance().getServer().getPluginManager().registerEvents(new me.emmiesa.flowercore.listeners.ChatListener(), FlowerCore.getInstance());
        FlowerCore.getInstance().getServer().getPluginManager().registerEvents(new CommandListener(), FlowerCore.getInstance());
        FlowerCore.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(FlowerCore.getInstance(), "BungeeCord");
        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] | Registered all listeners in " + timeTaken + "ms."));

    }

    public static void handlers() {
        long start = System.currentTimeMillis();

        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] | Registering all handlers..."));

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] | Registered all handlers in " + timeTaken + "ms."));
    }

    public static void done() {
        Bukkit.getConsoleSender().sendMessage(CC.translate(" "));
        Bukkit.getConsoleSender().sendMessage(CC.translate("[FlowerCore] Register was successful."));
    }
}