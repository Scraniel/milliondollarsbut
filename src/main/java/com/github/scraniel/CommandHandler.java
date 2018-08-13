package com.github.scraniel;

import com.github.scraniel.Commands.ICommand;
import com.github.scraniel.Commands.JokeCommand;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private Map<String, ICommand> commandMap;

    public CommandHandler()
    {
        // Create and populate the commandMap
        commandMap = new HashMap<>();

        // TODO: Source this out to a config file?
        commandMap.put(BotUtils.BOT_PREFIX + "joke", new JokeCommand());
    }

    /*
     * All this class will do is parse the input and route it to the applicable Command
     */
    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){
        String content = event.getMessage().getContent();

        // We only want to deal with bot commands
        if(content.startsWith(BotUtils.BOT_PREFIX)) {

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