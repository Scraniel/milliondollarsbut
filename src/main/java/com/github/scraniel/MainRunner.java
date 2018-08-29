package com.github.scraniel;

import com.github.scraniel.commands.JokeCommand;
import com.github.scraniel.commands.MillionDollarsButCommand;
import sx.blah.discord.api.IDiscordClient;

public class MainRunner {

    private static final String QUESTIONS_JSON = "questions.json";

    public static void main(String[] args){

        if(args.length != 1){
            System.out.println("Please enter the bots token as the first argument e.g java -jar thisjar.jar tokenhere");
            return;
        }

        IDiscordClient cli = BotUtils.getBuiltDiscordClient(args[0]);

        CommandHandler handler = new CommandHandler(cli);

        // TODO: Source this out to a config file?
        handler.registerCommand("joke", new JokeCommand(cli));
        handler.registerCommand("mdb", new MillionDollarsButCommand(QUESTIONS_JSON, cli));

        // Register a listener via the EventSubscriber annotation which allows for organisation and delegation of events
        cli.getDispatcher().registerListener(handler);

        // Only login after all events are registered otherwise some may be missed.
        cli.login();
    }

}