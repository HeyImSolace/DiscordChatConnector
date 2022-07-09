package de.heyimsolace.discordChatConnector.rest;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.heyimsolace.discordChatConnector.DiscordChatConnector;
import lombok.extern.java.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Server extends Thread{

    boolean running;
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);

    private final HttpServer server;
    private final int port;

    public Server(int port) {
        this.port = port;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 20);
            server.setExecutor(threadPoolExecutor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        server.start();
        DiscordChatConnector.log.info("Server started on port " + port);
    }

    public void registerContext(String path, HttpHandler handler) {
        DiscordChatConnector.log.info("Registering context: " + path);
        server.createContext(path, handler);
    }

    public void stopServer() {
        running = false;
    }
}
