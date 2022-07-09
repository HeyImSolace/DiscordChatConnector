package de.heyimsolace.discordChatConnector.pojo;

import lombok.Data;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PlayerListMessageContainer {
    private int maxplayers;
    private int playercnt;
    private Set<String> players = new HashSet<String>();
    private String ip;

    public PlayerListMessageContainer(List<ServerPlayer> players) {
        playercnt = players.size();
        maxplayers = ServerLifecycleHooks.getCurrentServer().getMaxPlayers();
        players.stream().forEach(e -> this.players.add(e.getName().getString()));
        ip = "???";
    }
}
