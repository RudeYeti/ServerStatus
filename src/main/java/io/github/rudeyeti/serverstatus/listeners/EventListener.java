package io.github.rudeyeti.serverstatus.listeners;

import io.github.rudeyeti.serverstatus.Config;
import io.github.rudeyeti.serverstatus.ServerStatus;
import io.github.rudeyeti.serverstatus.StatusMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        try {
            ServerStatus.onlinePlayers.add(event.getPlayer().getName());
            ServerStatus.channel.editMessageById(Config.messageId, StatusMessage.serverOn().build()).queue();
        } catch (NullPointerException ignored) {}
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        try {
        ServerStatus.onlinePlayers.remove(event.getPlayer().getName());
        ServerStatus.channel.editMessageById(Config.messageId, StatusMessage.serverOn().build()).queue();
        } catch (NullPointerException ignored) {}
    }
}
