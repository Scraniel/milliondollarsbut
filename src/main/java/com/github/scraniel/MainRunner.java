package com.github.scraniel;

import com.github.scraniel.commands.BallpitMessageCommand;
import com.github.scraniel.commands.JokeMessageCommand;
import com.github.scraniel.commands.MillionDollarsButMessageCommand;

public class MainRunner {

    private static final String QUESTIONS_JSON = "questions.json";

    public static void main(String[] args){

        if(args == null || args.length != 1){
            System.out.println("Please enter the bots token as the first argument e.g java -jar thisjar.jar tokenhere");
            return;
        }

        EventHandler handler = new EventHandler();
        handler.init(args[0]);

        // TODO: Source this out to a config file?
        handler.registerMessageCommand("joke", new JokeMessageCommand());
        handler.registerMessageCommand("mdb", new MillionDollarsButMessageCommand(QUESTIONS_JSON));
        handler.registerReactionCommand("ballpit", new BallpitMessageCommand());
    }
}