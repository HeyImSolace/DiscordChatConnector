package de.heyimsolace.discordChatConnector;

import de.heyimsolace.discordChatConnector.config.DefaultsMaker;
import de.heyimsolace.discordChatConnector.config.FileParser;

public class ModGlobals {

    public static final String CONFIG_DIR_NAME = "config/DiscordChatConnector";
    public static final String BOT_CONFIG_NAME = CONFIG_DIR_NAME + "/bot.cfg";
    public static final String PLAYER_MAP_NAME = CONFIG_DIR_NAME + "/playermap.cfg";
    public static final String MESSAGE_CFG_NAME = CONFIG_DIR_NAME + "/message.cfg";

    private static FileParser botCfg;
    public static FileParser getBotCfg() {
        if (botCfg == null) {
            botCfg = new FileParser(BOT_CONFIG_NAME, DefaultsMaker.botCfg());
        }
        return botCfg;
    }

    private static FileParser playerMap;
    public static FileParser getPlayerMap() {
        if (playerMap == null) {
            playerMap = new FileParser(PLAYER_MAP_NAME, DefaultsMaker.playerMap());
        }
        return playerMap;
    }

    private static FileParser messageCfg;
    public static FileParser getMessageCfg() {
        if (messageCfg == null) {
            messageCfg = new FileParser(MESSAGE_CFG_NAME, DefaultsMaker.messageCfg());
        }
        return messageCfg;
    }

    public static void reloadConfigs() {
        getBotCfg().loadFile();
        getPlayerMap().loadFile();
        getMessageCfg().loadFile();
    }

}
