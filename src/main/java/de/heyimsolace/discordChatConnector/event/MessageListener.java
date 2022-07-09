package de.heyimsolace.discordChatConnector.event;

import de.heyimsolace.discordChatConnector.pojo.Message;
import de.heyimsolace.discordChatConnector.rest.MessageRest;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MessageListener {

    @SubscribeEvent
    public void handleMessage(ServerChatEvent event) {
        Message msg = new Message();
        msg.setText(event.getMessage());
        msg.setUser(event.getPlayer().getName().getString());
        new MessageRest().sendMessage(msg);
    }
}
