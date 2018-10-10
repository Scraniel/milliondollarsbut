package com.github.scraniel.milliondollarsbutbot.commands;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class MillionDollarsButMessageCommandTest {

    private final String MILLION_DOLLARS_PREFIX = "You get a million dollars, but... ";

    @Test
    public void MillionDollarsButCommand_GetQuestionsTest(){
        MillionDollarsButMessageCommand command = new MillionDollarsButMessageCommand("testquestions.json", null);

        String question = command.getMessage();

        String[] validQuestions = new String[]{MILLION_DOLLARS_PREFIX + "You don't get a million dollars.", MILLION_DOLLARS_PREFIX + "You are now dead.", MILLION_DOLLARS_PREFIX + "You get two million dollars."};

        Assert.assertTrue(Arrays.asList(validQuestions).contains(question));
    }

}
