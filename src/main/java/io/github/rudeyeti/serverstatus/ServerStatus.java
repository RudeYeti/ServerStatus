package io.github.rudeyeti.serverstatus;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import io.github.rudeyeti.serverstatus.listeners.DiscordSRVListener;
import io.github.rudeyeti.serverstatus.listeners.EventListener;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class ServerStatus extends JavaPlugin {

    public static Plugin plugin;
    public static Server server;
    public static Logger logger;
    public static TextChannel channel;
    public static List<String> onlinePlayers = new ArrayList<>();

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        plugin = getPlugin(this.getClass());
        server = plugin.getServer();
        logger = this.getLogger();
        Config.config = plugin.getConfig();
        plugin.saveDefaultConfig();

        if (Config.validateConfig()) {
            Config.updateConfig();
        } else {
            server.getPluginManager().disablePlugin(plugin);
            return;
        }

        // Should almost always be none, unless the plugin is somehow reloaded.
        ServerStatus.server.getOnlinePlayers().forEach((player) -> {
            onlinePlayers.add(player.getName());
        });

        server.getPluginManager().registerEvents(new EventListener(), this);
        DiscordSRV.api.subscribe(new DiscordSRVListener());
    }

    @Override
    public void onDisable() {
        try {
            channel.editMessageById(Config.messageId, StatusMessage.serverOff().build()).complete();
            DiscordSRV.api.unsubscribe(new DiscordSRVListener());
        } catch (NullPointerException ignored) {}
    }
}
