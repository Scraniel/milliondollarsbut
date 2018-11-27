package com.github.scraniel.milliondollarsbutbot.commands;

import com.github.scraniel.basebot.commands.AbstractMessageCommand;
import com.github.scraniel.milliondollarsbutbot.QuestionService;
import com.github.scraniel.milliondollarsbutbot.data.QuestionResponse;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;
import java.util.List;

public class StatsMessageCommand extends AbstractMessageCommand {

    private final String NO_QUESTIONS_ANSWERED = "You haven't answered any questions yet, <@%s>! Answer some questions and get you some hypothetical money!";

    private final String INTRO = "Congratulations <@%s>, you're rich! You have $%d million! You've been through some shit though.";
    private final String NO_YES_INTRO = "Well, you don't have money, but at least you have standards.";
    private final String NO_ANSWERS_PREFIX = "These things are a hard no:";
    private final String YES_ANSWERS_PREFIX = "You'll apparently do the following things for money:";
    private final String OUTRO = "Well, is it worth it? Does <@%s> have a pretty shit existence now?";
    private final String QUESTION_FORMAT = "    - %s (ID: %s)\n";
    private String message = "";

    @Override
    protected boolean setUp() {
        boolean correctEvent = super.setUp() && currentEvent instanceof MessageReceivedEvent;

        if(!correctEvent)
        {
            return false;
        }

        boolean fullStats = currentArguments.length == 1 && "full".compareToIgnoreCase(currentArguments[0]) == 0;

        // So we can get the user who's asking for stats TODO: implement requesting others after
        MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent)currentEvent;
        IUser user = messageReceivedEvent.getAuthor();

        List<QuestionResponse> responses = QuestionService.getInstance().getResponses(user.getLongID());
        if(responses == null || responses.size() == 0)
        {
            message = String.format(NO_QUESTIONS_ANSWERED, user.getLongID());
            return true;
        }

        List<QuestionResponse> yesResponses = new ArrayList<>();
        List<QuestionResponse> noResponses = new ArrayList<>();

        // Split up responses so we can format a bit better
        for(QuestionResponse response : responses) {
            if (response.getAnswer()) {
                yesResponses.add(response);
            }
            else {
                noResponses.add(response);
            }
        }

        StringBuilder builder = new StringBuilder();

        if(yesResponses.size() > 0) {
            long userId = user.getLongID();
            int money = QuestionService.getInstance().getResponses(userId).size();
            builder.append(String.format(INTRO, userId, money));
        }
        else {
            builder.append(NO_YES_INTRO);
        }
        builder.append("\n\n");

        if(noResponses.size() > 0 && fullStats)
        {
            appendResponses(builder, NO_ANSWERS_PREFIX, noResponses);
            if(yesResponses.size() > 0) {
                builder.append("\n\nAnd yet... ");
            }
        }

        if(yesResponses.size() > 0)
        {
            appendResponses(builder, YES_ANSWERS_PREFIX, yesResponses);
            builder.append("\n");
            builder.append(String.format(OUTRO, user.getLongID()));
        }

        message = builder.toString();
        return true;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private void appendResponses(StringBuilder builder, String prefix, List<QuestionResponse> responses)
    {
        builder.append(prefix);
        builder.append("\n\n");

        for(QuestionResponse response : responses){
            builder.append(String.format(QUESTION_FORMAT, QuestionService.getInstance().getQuestion(response.getQuestionId()), response.getQuestionId()));
        }
    }
}
