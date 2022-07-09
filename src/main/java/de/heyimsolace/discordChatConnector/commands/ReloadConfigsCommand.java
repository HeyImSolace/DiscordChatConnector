package de.heyimsolace.discordChatConnector.commands;

import com.mojang.brigadier.CommandDispatcher;
import de.heyimsolace.discordChatConnector.ModGlobals;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ReloadConfigsCommand {

    public ReloadConfigsCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("dcc")
                            .then(Commands.literal("reload"))
                            .executes((command) -> {
                                return commandAction();
                            }));
    }

    private int commandAction() {
        ModGlobals.reloadConfigs();
        return 0;
    }
}
