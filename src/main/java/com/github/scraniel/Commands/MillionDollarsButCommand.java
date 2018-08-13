package com.github.scraniel.Commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.github.scraniel.BotUtils;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MillionDollarsButCommand extends MessageCommand{

    private ArrayList<String> questions;
    private Random rng;
    private final String MILLION_DOLLARS_PREFIX = "You get a million dollars, but... ";

    public MillionDollarsButCommand(String fileName){

        // Get some of that RNG
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
            questions = new ArrayList<String>(Arrays.asList(questionArray));
        }catch (Exception e){}

        if(questions == null)
        {
            // Will only get here if the above read fails, don't want to crash anything
            questions = new ArrayList<>();
        }

        return questions;
    }

    @Override
    public String getMessage(String[] args) {
        return MILLION_DOLLARS_PREFIX + questions.get(rng.nextInt(questions.size()));
    }
}
