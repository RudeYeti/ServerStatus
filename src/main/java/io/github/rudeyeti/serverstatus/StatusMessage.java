package io.github.rudeyeti.serverstatus;

import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;

import java.awt.*;

public class StatusMessage {
    public static EmbedBuilder serverOn() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        int onlinePlayers = ServerStatus.onlinePlayers.size();

        embedBuilder.setTitle(Config.serverAddress, null);
        embedBuilder.setColor(new Color(0x759965));

        embedBuilder.addField(
                "Status:",
                "Online",
                false
        );

        embedBuilder.addField(
                "Online Players:",
                onlinePlayers + "/" + ServerStatus.server.getMaxPlayers(),
                false
        );

        if (onlinePlayers > 0) {
            final String[] playerList = {""};

            ServerStatus.onlinePlayers.forEach((player) -> {
                playerList[0] += player + "\n";
            });

            // Discord has an embed field character limit of 1024.
            if (playerList[0].length() > 1024) {
                playerList[0] = playerList[0].substring(0, 1017);
                playerList[0] = playerList[0].substring(0, playerList[0].lastIndexOf("\n"));
                playerList[0] = playerList[0].concat("More...");
            }

            embedBuilder.addField(
                    "Player List:",
                    playerList[0],
                    false
            );
        }

        return embedBuilder;
    }

    public static EmbedBuilder serverOff() {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle(Config.serverAddress, null);
        embedBuilder.setColor(new Color(0xBF5843));

        embedBuilder.addField(
                "Status:",
                "Offline",
                false
        );

        return embedBuilder;
    }
}
