package com.github.scraniel.commands;

import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.IDiscordClient;

public abstract class AbstractCommand implements ICommand{

    protected IDiscordClient discordContext;
    protected Event currentEvent;
    protected String[] currentArguments;

    public AbstractCommand()
    {
        this(null);
    }

    public AbstractCommand (IDiscordClient context) {
        discordContext = context;
    }

    @Override
    public void run (Event event, String[] args) {
        currentEvent = event;
        currentArguments = args;

        // Only bother running the command if setUp passes
        if(setUp())
        {
            doCommand();
        }
        cleanUp();

        currentEvent = null;
        currentArguments = null;
    }

    @Override
    public void setDiscordContext(IDiscordClient context)
    {
        discordContext = context;
    }

    abstract boolean setUp();
    abstract void doCommand();
    abstract void cleanUp();
}
