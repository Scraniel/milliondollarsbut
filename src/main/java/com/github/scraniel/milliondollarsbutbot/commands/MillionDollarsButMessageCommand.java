package com.github.scraniel.milliondollarsbutbot.commands;


import com.github.scraniel.basebot.commands.AbstractMessageCommand;
import com.github.scraniel.milliondollarsbutbot.QuestionService;
import sx.blah.discord.api.IDiscordClient;

public class MillionDollarsButMessageCommand extends AbstractMessageCommand {

    // If we add any other params, consider builder pattern
    public MillionDollarsButMessageCommand(IDiscordClient context){
        super(context);
    }

    public MillionDollarsButMessageCommand()
    {
        super();
    }

    @Override
    public String getMessage() {
        return QuestionService.getInstance().getQuestion();
    }
}
