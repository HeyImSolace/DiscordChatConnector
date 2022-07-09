package de.heyimsolace.discordChatConnector.config;
import java.util.HashMap;

public class PlayerMapFileParser extends FileParser {

    public PlayerMapFileParser(String configFileName, HashMap<String, String> defaults) {
        super(configFileName, defaults);
    }

    private HashMap<String, String> playerMapInversed;
    private HashMap<String, String> getPlayerMapInversed() {
        if (playerMapInversed == null) {
            playerMapInversed = new HashMap<>();
            for (String key : values.keySet()) {
                playerMapInversed.put(values.get(key), key);
            }
        }
        return playerMapInversed;
    }

    @Override
    public void loadFile() {
        super.loadFile();
        playerMapInversed = null;
        getPlayerMapInversed();
    }

    public String getMinecraftName(String discordId) {

        return getPlayerMapInversed().get(discordId);
    }

    public String getDiscordID(String playerName) {
        return getPlayerMapInversed().get(playerName);
    }
}
