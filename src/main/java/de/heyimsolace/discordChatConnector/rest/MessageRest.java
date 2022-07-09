package de.heyimsolace.discordChatConnector.rest;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.heyimsolace.discordChatConnector.DiscordChatConnector;
import de.heyimsolace.discordChatConnector.ModGlobals;
import de.heyimsolace.discordChatConnector.pojo.Message;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class MessageRest implements HttpHandler {

    private final String boturl;
    private final String apiPath;


    public MessageRest() {
        boturl = ModGlobals.getPlayerListCfg().getValue("boturl");
        apiPath = ModGlobals.getPlayerListCfg().getValue("messageapi");
    }

    /**
     * Sends the Message to the Discord bot
     */
    public void sendMessage(Message msg) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(msg);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(boturl + apiPath))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
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
            DiscordChatConnector.log.warn("Check your Config. The BotURL and/or Message api path is not valid!");
        }
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {

            switch (exchange.getRequestMethod()) {
                case "POST":
                    Gson gson = new Gson();
                    Message msg = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Message.class);
                    ServerLifecycleHooks.getCurrentServer()
                            .getPlayerList().getPlayers().forEach(p -> {
                                p.sendMessage(new TextComponent(msg.buildChatMessage()), UUID.randomUUID());
                            });
                    exchange.sendResponseHeaders(200, 0);
                    exchange.close();
                    break;
                default:
                    exchange.sendResponseHeaders(405, 0);
                    exchange.getResponseBody().close();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
