package com.github.scraniel.commands;

import sx.blah.discord.api.events.Event;

public interface ICommand {

    void run(Event event, String[] args);
}
