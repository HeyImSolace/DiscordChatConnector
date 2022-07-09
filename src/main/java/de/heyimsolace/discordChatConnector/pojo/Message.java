package de.heyimsolace.discordChatConnector.pojo;

import de.heyimsolace.discordChatConnector.ModGlobals;
import lombok.Data;

@Data
public class Message {
    private String user;
    private String text;

    public String buildChatMessage() {
        return ModGlobals.getMessageCfg().getValue("prefix") + user + ": " + text;
    }
}
