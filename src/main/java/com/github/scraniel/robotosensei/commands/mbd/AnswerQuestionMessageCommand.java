package com.github.scraniel.robotosensei.commands.mbd;

import com.github.scraniel.basebot.commands.AbstractMessageCommand;
import com.github.scraniel.robotosensei.mdb.QuestionService;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

public class AnswerQuestionMessageCommand extends AbstractMessageCommand {

    private final String USER_ERROR = "Dude c'mon, to answer a MDB question please use the following format: `/answer <question ID> <response>`. For example: `/answer 40 yes`";
    private final String NO_SUCH_QUESTION = "Uhh, there's no question with ID %s.";
    private final String ANSWER_YES_OR_NO = "What are you trying to do? You gotta answer with either 'yes' or 'no'.";
    private final String VALID_PREFIX_FORMAT = "Cool, answer recorded. <@%s>, you've currently got $%d million! To see your full stats, try /stats";
    private final String ALREADY_ANSWERED = "You already answered that one, cheater. Try /stats to see what you've got.";
    private String response = "";

    @Override
    protected boolean setUp() {
        boolean correctEvent = super.setUp() && currentEvent instanceof MessageReceivedEvent;

        if(!correctEvent)
        {
            return false;
        }

        boolean userError = currentArguments.length != 2;

        if(userError)
        {
            response = USER_ERROR;
            return true;
        }

        // This event REQUIRES the user to type something in order to happen (ie. it's a classic / event)
        MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent)currentEvent;

        IUser responding = messageReceivedEvent.getAuthor();
        String questionId = currentArguments[0];
        String answer = currentArguments[1];

        if(QuestionService.getInstance().getQuestion(questionId) == null )
        {
            response = String.format(NO_SUCH_QUESTION, questionId);
            return true;
        }
        if(answer.compareToIgnoreCase("yes") != 0 && answer.compareToIgnoreCase("no") != 0)
        {
            response = ANSWER_YES_OR_NO;
            return true;
        }

        boolean answerBool = answer.compareToIgnoreCase("yes") == 0;

        // At this point, we know the response is correct and can be recorded
        long userId = responding.getLongID();
        String userName = responding.getDisplayName(messageReceivedEvent.getGuild());

        if(!QuestionService.getInstance().recordResponse(answerBool, questionId, userId))
        {
            response = ALREADY_ANSWERED;
            return true;
        }

        int money = QuestionService.getInstance().getPositiveResponseCount(userId);

        response = String.format(VALID_PREFIX_FORMAT, userId, money);
        return true;
    }

    @Override
    public String getMessage() {
        return response;
    }

    @Override
    protected void cleanUp()
    {
        super.cleanUp();
        response = "";
    }
}
