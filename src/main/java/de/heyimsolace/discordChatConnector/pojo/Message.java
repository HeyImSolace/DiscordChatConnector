package de.heyimsolace.discordChatConnector.pojo;

import de.heyimsolace.discordChatConnector.ModGlobals;
import lombok.Data;

@Data
public class Message {
    //Discord Userid
    private String user;
    //Discord or Minecraft Name
    private String name;
    //Message Text
    private String text;

    public String buildChatMessage() {
        String minecraftName = ModGlobals.getPlayerMap().getMinecraftName(user); // lookup Minecraft name from Discord ID
        if (minecraftName == null) { // if there is no matched Name to the DiscordID, use the discord name instead
            minecraftName = name;
        }
        String template = ModGlobals.getMessageCfg().getValue("template");
        template.replace("{USER}", minecraftName);
        template.replace("{MESSAGE}", text);
        return template;
    }
}
