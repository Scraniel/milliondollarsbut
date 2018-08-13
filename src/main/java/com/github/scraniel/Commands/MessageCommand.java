package com.github.scraniel.Commands;

import com.github.scraniel.BotUtils;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/*
 * A command that sends a message to the channel
 */
public abstract class MessageCommand implements ICommand{

    public abstract String getMessage(String[] args);

    @Override
    public void run(Event event, String[] args) {
        // We only handle ChannelEvents here (we need to be able to get which channel to post to)
        if(!(event instanceof ChannelEvent)){
            return;
        }

        ChannelEvent channelEvent = (ChannelEvent)event;

        BotUtils.sendMessage(channelEvent.getChannel(), getMessage(args));
    }
}
