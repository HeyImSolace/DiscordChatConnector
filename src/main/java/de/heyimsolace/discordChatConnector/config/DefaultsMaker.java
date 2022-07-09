package de.heyimsolace.discordChatConnector.config;

import java.util.HashMap;

public class DefaultsMaker {

    public static HashMap<String, String> botCfg() {
        HashMap<String, String> cfg = new HashMap<>();
        cfg.put("boturl", "https://UrlToYourBot.com/restAPI");
        cfg.put("token", "token");
        cfg.put("channelId", "channelId");
        cfg.put("messageapi", "/pathToMessageRestAPI");
        cfg.put("playerlistapi", "/pathToPlayerListRestAPI");
        return cfg;
    }

    public static HashMap<String, String> playerMap() {
        HashMap<String, String> config = new HashMap<>();
        config.put("MinecraftName", "DiscordID");
        config.put("notch:", "<@012345678901234567>");
        return config;
    }

    public static HashMap<String, String> messageCfg() {
        HashMap<String, String> config = new HashMap<>();
        config.put("prefix", "§f[§9DISCORD§f]");
        return config;
    }
}
