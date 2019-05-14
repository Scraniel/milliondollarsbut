package com.github.scraniel.robotosensei.commands;

import com.github.scraniel.basebot.commands.AbstractMessageCommand;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IReaction;

public class BallpitMessageCommand extends AbstractMessageCommand {

    IChannel oldChannel;
    IReaction reaction;
    String oldMessage;
    private final String BALLPIT_NAME = "ballpit";

    @Override
    public boolean setUp() {

        // Gets the channel reaction is from
        if(!super.setUp() || !(currentEvent instanceof ReactionEvent))
        {
            return false;
        }

        // We want a majority vote!
        // TODO: Right now is not majority, it's set low for testing purposes
        reaction = ((ReactionEvent)currentEvent).getReaction();
        if(reaction.getCount() < 3)
        {
            return false;
        }

        oldChannel = currentChannel;

        // Set current channel to ballpit
        for (IChannel channel : currentEvent.getClient().getChannels()) {
            if(channel.getName().equals(BALLPIT_NAME)) {
                currentChannel = channel;
                break;
            }
        }

        return oldChannel != currentChannel;
    }

    @Override
    public void doCommand()
    {
        // We've hit the threshold, delete the message!
        oldMessage = reaction.getMessage().getContent();
        reaction.getMessage().delete();
        super.doCommand();
    }

    @Override
    public String getMessage() {
        return "@here - Someone said something dumb:```" + oldMessage + "```";
    }
}