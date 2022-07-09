package de.heyimsolace.discordChatConnector.event;

import de.heyimsolace.discordChatConnector.ModGlobals;
import de.heyimsolace.discordChatConnector.pojo.Message;
import de.heyimsolace.discordChatConnector.rest.MessageRest;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MessageListener {

    @SubscribeEvent
    public void handleMessage(ServerChatEvent event) {
        Message msg = new Message();
        msg.setUser(ModGlobals.getPlayerMap().getDiscordID(event.getPlayer().getName().getString()));
        msg.setName(event.getPlayer().getName().getString());
        msg.setText(event.getMessage());
        new MessageRest().sendMessage(msg);
    }
}
