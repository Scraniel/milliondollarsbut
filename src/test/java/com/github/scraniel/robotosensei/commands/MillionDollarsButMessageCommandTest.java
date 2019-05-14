package com.github.scraniel.robotosensei.commands;

import com.github.scraniel.robotosensei.mdb.QuestionService;
import com.github.scraniel.robotosensei.commands.mbd.MillionDollarsButMessageCommand;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class MillionDollarsButMessageCommandTest {

    private final String MILLION_DOLLARS_PREFIX = "You get a million dollars, but... ";

    @Test
    public void MillionDollarsButCommand_GetQuestionsTest(){
        MillionDollarsButMessageCommand command = new MillionDollarsButMessageCommand();
        QuestionService.getInstance().init("testquestions.json");

        String question = command.getMessage();

        String[] validQuestions = new String[]{"Question 0: " + MILLION_DOLLARS_PREFIX + "You don't get a million dollars.", "Question 1: " + MILLION_DOLLARS_PREFIX + "You are now dead.", "Question 2: " + MILLION_DOLLARS_PREFIX + "You get two million dollars."};

        Assert.assertTrue("Actual output: " + question, Arrays.asList(validQuestions).contains(question));
    }

}
