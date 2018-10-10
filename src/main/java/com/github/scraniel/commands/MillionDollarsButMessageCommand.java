package com.github.scraniel.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scraniel.BotUtils;
import sx.blah.discord.api.IDiscordClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MillionDollarsButMessageCommand extends AbstractMessageCommand {

    private ArrayList<String> questions;
    private Random rng;
    private final String MILLION_DOLLARS_PREFIX = "You get a million dollars, but... ";

    // If we add any other params, consider builder pattern
    public MillionDollarsButMessageCommand(String fileName, IDiscordClient context){
        super(context);
        init(fileName);
    }

    public MillionDollarsButMessageCommand(String fileName)
    {
        super();
        init(fileName);
    }

    private void init(String fileName)
    {
        rng = new Random();
        questions = loadQuestionsFromFile(fileName);
    }

    private ArrayList<String> loadQuestionsFromFile(String fileName)
    {
        // Read the json from the resources folder
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String json = BotUtils.convertInputStreamToString(classloader.getResourceAsStream(fileName));
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> questions = null;

        try
        {
            String[] questionArray = mapper.readValue(json, String[].class);
            questions = new ArrayList<>(Arrays.asList(questionArray));
        }catch (Exception e){}

        if(questions == null)
        {
            // Will only get here if the above read fails, don't want to crash anything
            questions = new ArrayList<>();
        }

        return questions;
    }

    @Override
    public String getMessage() {
        return MILLION_DOLLARS_PREFIX + questions.get(rng.nextInt(questions.size()));
    }
}
