package de.heyimsolace.discordChatConnector.event;

import de.heyimsolace.discordChatConnector.pojo.PlayerListMessageContainer;
import de.heyimsolace.discordChatConnector.rest.PlayerListRest;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JoinLeaveListener {
    /**
     * Called whenever a Player joins the server
     *
     * @param event
     */
    @SubscribeEvent
    public void handleLogin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerListMessageContainer plmc = new PlayerListMessageContainer(ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers());
        new PlayerListRest().sendMessage(plmc);
    }

    /**
     * Called whenever a Player leaves the server
     *
     * @param event
     */
    @SubscribeEvent
    public void handleLogout(PlayerEvent.PlayerLoggedOutEvent event) {

        List<ServerPlayer> plist = ServerLifecycleHooks.getCurrentServer()
                .getPlayerList()
                .getPlayers()
                .stream()
                .filter(p -> !p.getName()
                        // Filters the leaving player, because he is still part of the list at the time of the event
                        .equals(event.getPlayer().getName()))
                .collect(Collectors.toList());

        new PlayerListRest().sendMessage(new PlayerListMessageContainer(plist));
    }
}
