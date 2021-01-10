package io.github.rudeyeti.serverstatus.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordReadyEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Guild;
import io.github.rudeyeti.serverstatus.Config;
import io.github.rudeyeti.serverstatus.ServerStatus;
import io.github.rudeyeti.serverstatus.StatusMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiscordSRVListener {
    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent event) {
        Guild guild = DiscordSRV.getPlugin().getMainGuild();
        ServerStatus.channel = guild.getTextChannelById(Config.channelId);

        if (Config.messageId.isEmpty()) {
            ServerStatus.channel.sendMessage(StatusMessage.serverOn().build()).queue((message) -> {
                try {
                    Path config = new File(ServerStatus.plugin.getDataFolder(), "config.yml").toPath();
                    String content = new String(Files.readAllBytes(config));
                    content = content.replaceAll("message-id: \"\"", "message-id: \"" + message.getId() + "\"");

                    Files.write(config, content.getBytes());
                    ServerStatus.plugin.reloadConfig();
                    Config.updateConfig();
                } catch (IOException error) {
                    error.printStackTrace();
                }
            });
        } else {
            ServerStatus.channel.editMessageById(Config.messageId, StatusMessage.serverOn().build()).queue();
        }
    }
}
