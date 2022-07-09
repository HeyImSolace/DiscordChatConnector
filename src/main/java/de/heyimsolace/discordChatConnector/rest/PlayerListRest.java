package de.heyimsolace.discordChatConnector.rest;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.heyimsolace.discordChatConnector.DiscordChatConnector;
import de.heyimsolace.discordChatConnector.ModGlobals;
import de.heyimsolace.discordChatConnector.pojo.PlayerListMessageContainer;
import de.heyimsolace.discordChatConnector.pojo.PlayerMapping;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PlayerListRest implements HttpHandler {


    private final String boturl;
    private final String apiPath;

    public PlayerListRest() {
        boturl = ModGlobals.getBotCfg().getValue("boturl");
        apiPath = ModGlobals.getBotCfg().getValue("playerlistapi");
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
        if (exchange.getRequestURI().toString().endsWith("/match")) {
            switch (exchange.getRequestMethod()) {
                case "POST":
                    Gson gson = new Gson();
                    PlayerMapping pmap = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), PlayerMapping.class);
                    ModGlobals.getPlayerMap().putValue(pmap.getMinecraftUserName(), pmap.getDiscordUserId());
                    ModGlobals.getPlayerMap().saveConfigFile();
                    exchange.sendResponseHeaders(200, 0);
                    exchange.close();
                    break;
                default:
                    exchange.sendResponseHeaders(405, -1);
                    exchange.getResponseBody().close();
                    break;
            }
        }
            switch (exchange.getRequestMethod()) {
                case "GET":
                    List<ServerPlayer> plist = ServerLifecycleHooks.getCurrentServer()
                            .getPlayerList()
                            .getPlayers();
                    Gson gson = new Gson();
                    String json = gson.toJson(plist);
                    exchange.sendResponseHeaders(200, json.length());
                    exchange.getResponseBody().write(json.getBytes());
                    exchange.getResponseBody().close();
                    exchange.close();
                    break;
                default:
                    exchange.sendResponseHeaders(405, -1);
                    exchange.getResponseBody().close();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the Message to the Discord Webhook
     */
    public void sendMessage(PlayerListMessageContainer plist) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(plist);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(boturl + apiPath))
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .exceptionally(t -> {
                        System.out.println("Exception: " + t.getMessage());
                        return null;
                    });
        } catch (IllegalArgumentException e) {
            DiscordChatConnector.log.warn("Check your Config. The BotURL and/or Playerlist api path is not valid!");
        }
    }

}
