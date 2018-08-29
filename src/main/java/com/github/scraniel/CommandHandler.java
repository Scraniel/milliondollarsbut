package com.github.scraniel;

import com.github.scraniel.commands.ICommand;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * All this class will do is parse the input and route it to the applicable Command
 */
public class CommandHandler {

    private Map<String, ICommand> commandMap = null;
    private IDiscordClient discordContext = null;
    public static final String BOT_PREFIX = "/";

    public CommandHandler()
    {
        commandMap = new HashMap<>();
    }

    public CommandHandler(IDiscordClient context)
    {
        // Store discord client context
        discordContext = context;

        // Create the commandMap
        commandMap = new HashMap<>();
    }

    public void init(String token)
    {
        IDiscordClient cli = BotUtils.getBuiltDiscordClient(token);

        // Register a listener via the EventSubscriber annotation which allows for organisation and delegation of events
        cli.getDispatcher().registerListener(this);

        // Only login after all events are registered otherwise some may be missed.
        cli.login();
    }

    public void registerCommand(String commandName, ICommand command)
    {
        command.setDiscordContext(discordContext);
        commandMap.put(BOT_PREFIX + commandName, command);
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){
        String content = event.getMessage().getContent();

        // We only want to deal with bot commands
        if(content.startsWith(BOT_PREFIX)) {

            // Arguments to send will be following the command
            String[] splitContent = content.split(" ");
            String[] args = Arrays.copyOfRange(splitContent, 1, splitContent.length);
            String command = splitContent[0];

            if(commandMap.containsKey(command)){
                commandMap.get(command).run(event, args);
            }
        }
    }
}