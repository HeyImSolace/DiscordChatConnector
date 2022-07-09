package de.heyimsolace.discordChatConnector;

import de.heyimsolace.discordChatConnector.commands.ReloadConfigsCommand;
import de.heyimsolace.discordChatConnector.event.JoinLeaveListener;
import de.heyimsolace.discordChatConnector.event.MessageListener;
import de.heyimsolace.discordChatConnector.rest.MessageRest;
import de.heyimsolace.discordChatConnector.rest.PlayerListRest;
import de.heyimsolace.discordChatConnector.rest.Server;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("discordchatconnector")
//@Mod.EventBusSubscriber(modid = DiscordPlayerListWebhook.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class DiscordChatConnector {
    public static final String MODID = "discordchatconnector";
    public static Server s;
    public static Logger log = LogManager.getLogger(MODID);


    public DiscordChatConnector() {
        ModGlobals.reloadConfigs();
        // Register the event listener & Mod
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new JoinLeaveListener());
        MinecraftForge.EVENT_BUS.register(new MessageListener());

        // Initialize the REST API
        s = new Server(8082);
        s.registerContext("/message", new MessageRest());
        s.registerContext("/playerlist", new PlayerListRest());
        s.start();

    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        new ReloadConfigsCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

}
