package com.github.scraniel.milliondollarsbutbot;

import com.github.scraniel.basebot.EventHandler;
import com.github.scraniel.milliondollarsbutbot.commands.AnswerQuestionMessageCommand;
import com.github.scraniel.milliondollarsbutbot.commands.BallpitMessageCommand;
import com.github.scraniel.milliondollarsbutbot.commands.JokeMessageCommand;
import com.github.scraniel.milliondollarsbutbot.commands.MillionDollarsButMessageCommand;
import com.github.scraniel.milliondollarsbutbot.commands.StatsMessageCommand;
import com.github.scraniel.milliondollarsbutbot.data.Question;

public class MainRunner {

    private static final String QUESTIONS_JSON = "questions.json";

    public static void main(String[] args){

        if(args == null || args.length != 1){
            System.out.println("Please enter the bots token as the first argument e.g java -jar thisjar.jar tokenhere");
            return;
        }

        EventHandler handler = new EventHandler();
        handler.init(args[0]);

        QuestionService.getInstance().init(QUESTIONS_JSON);

        // TODO: Source this out to a config file?
        handler.registerMessageEvent("joke", new JokeMessageCommand());
        handler.registerMessageEvent("mdb", new MillionDollarsButMessageCommand());
        handler.registerMessageEvent("answer", new AnswerQuestionMessageCommand());
        handler.registerMessageEvent("stats", new StatsMessageCommand());
        handler.registerReactionEvent("ballpit", new BallpitMessageCommand());
    }
}