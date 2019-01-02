package com.github.scraniel.milliondollarsbutbot;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scraniel.basebot.BotUtils;
import com.github.scraniel.milliondollarsbutbot.data.Question;
import com.github.scraniel.milliondollarsbutbot.data.QuestionResponse;
import com.koloboke.collect.impl.hash.Hash;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class QuestionService {

    private static final QuestionService INSTANCE = new QuestionService();

    /*
     * TODO: Responses is what will eventually be stored in a DB
     * TODO: For responses / positive count, consider just having one data structure for the user
     */
    private Map<Long, List<QuestionResponse>> responses;
    private Map<Long, Integer> positiveResponsesCount;
    private Map<String, String> questions;
    private Random rng;
    private boolean initialized = false;

    private QuestionService() {
        responses = new HashMap<>();
        positiveResponsesCount = new HashMap<>();
        rng = new Random();
    }

    public static QuestionService getInstance() {
        return INSTANCE;
    }

    /**
     * Initializes the users and questions from an input file
     *
     * @param fileName file to load questions from
     */
    public void init(String fileName) {

        questions = loadQuestionsFromFile(fileName);
        initialized = true;
    }

    /**
     * Fetches a random question.
     * TODO: Once multi-part questions are added, will have to generate using both halves
     *
     * @return the question to be asked (should contain ID for future lookup)
     */
    public String getQuestion() {
        if(!initialized) {
            return "";
        }

        String id = Integer.toString(rng.nextInt(questions.size()));
        String question = getQuestion(id);

        return new Question(id, question).get();
    }

    /**
     * Fetches a question with a specific ID
     *
     * @param id the ID of the question
     * @return the question
     */
    public String getQuestion(String id) {
        if(!initialized) {
            return "";
        }

        return questions.get(id);
    }

    /**
     * Stores a response to a question from a given user
     *
     * @param answer Whether or not the user would do the thing
     * @param questionID The ID of the question answering
     * @param userID The ID of the user answering
     * @return false if already recorded
     */
    public boolean recordResponse(boolean answer, String questionID, long userID) {
        QuestionResponse response = new QuestionResponse(answer, questionID);
        List<QuestionResponse> userResponses = responses.get(userID);
        Integer positiveResponseCount = positiveResponsesCount.get(userID);

        if(userResponses == null) {
            userResponses = new ArrayList<>();
            responses.put(userID, userResponses);
            positiveResponseCount = 0;
        }

        // TODO: Inefficient, but will do for now
        for(QuestionResponse currentResponse : userResponses) {
            if(currentResponse.getQuestionId().compareTo(questionID) == 0) {
                return false;
            }
        }

        userResponses.add(response);
        int newPositives = response.getAnswer() ? positiveResponseCount + 1 : positiveResponseCount;
        positiveResponsesCount.put(userID, newPositives);
        return true;
    }

    /**
     * Gets all responses for a given user
     *
     * @param userID
     * @return
     */
    public List<QuestionResponse> getResponses(long userID){
        return responses.get(userID);
    }

    public int getPositiveResponseCount(long userID)
    {
        Integer count = positiveResponsesCount.get(userID);
        return count == null ? 0 : count;
    }

    private Map<String, String> loadQuestionsFromFile(String fileName)
    {
        if(fileName == null)
        {
            // TODO: Consider throwing exception (if we don't support adding questions at runtime), at the very least logging
            return null;
        }

        // Read the json from the resources folder
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String json = BotUtils.convertInputStreamToString(classloader.getResourceAsStream(fileName));
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> questions = null;
        HashMap<String, String> questionsMap = new HashMap<>();

        // Read the questions into an array
        // TODO: LOGGING!
        try
        {
            String[] questionArray = mapper.readValue(json, String[].class);
            questions = new ArrayList<>(Arrays.asList(questionArray));
        }catch (Exception e){}

        if(questions == null)
        {
            // Will only get here if the above read fails, don't want to crash anything
            return questionsMap;
        }

        // These base questions don't yet have IDs associated with them, so we just use numbers
        for(int i = 0; i < questions.size(); i++)
        {
            questionsMap.put(Integer.toString(i), questions.get(i));
        }

        return questionsMap;
    }
}
