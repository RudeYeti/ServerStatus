package io.github.rudeyeti.serverstatus;

import org.bukkit.configuration.Configuration;

public class Config {

    public static Configuration config;
    public static String channelId;
    public static String serverAddress;
    public static String messageId;

    public static void updateConfig() {
        config = ServerStatus.plugin.getConfig();
        channelId = config.getString("channel-id");
        serverAddress = config.getString("server-address");
        messageId = config.getString("message-id");
    }

    private static String message(String option, String message) {
        return "The " + option + " value in the configuration must be " + message;
    }

    public static boolean validateConfig() {
        if (!(config.get("channel-id") instanceof String)) {
            ServerStatus.logger.warning(message("channel-id", "enclosed in quotes."));
        } else if (config.get("channel-id").equals("##################")) {
            ServerStatus.logger.warning(message("channel-id", "modified from ##################."));
        } else if (!(config.get("server-address") instanceof String)) {
            ServerStatus.logger.warning(message("server-address", "enclosed in quotes."));
        } else if (config.get("server-address").equals("example.com:25565")) {
            ServerStatus.logger.warning(message("server-address", "modified from example.com:25565."));
        } else {
            return true;
        }
        return false;
    }
}
