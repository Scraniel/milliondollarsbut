package com.github.scraniel;

import com.github.scraniel.commands.JokeCommand;
import com.github.scraniel.commands.MillionDollarsButCommand;

public class MainRunner {

    private static final String QUESTIONS_JSON = "questions.json";

    public static void main(String[] args){

        if(args == null || args.length != 1){
            System.out.println("Please enter the bots token as the first argument e.g java -jar thisjar.jar tokenhere");
            return;
        }

        CommandHandler handler = new CommandHandler();
        handler.init(args[0]);

        // TODO: Source this out to a config file?
        handler.registerCommand("joke", new JokeCommand());
        handler.registerCommand("mdb", new MillionDollarsButCommand(QUESTIONS_JSON));
    }

}