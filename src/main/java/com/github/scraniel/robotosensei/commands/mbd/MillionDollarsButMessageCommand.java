package com.github.scraniel.robotosensei.commands.mbd;


import com.github.scraniel.basebot.commands.AbstractMessageCommand;
import com.github.scraniel.robotosensei.mdb.QuestionService;

public class MillionDollarsButMessageCommand extends AbstractMessageCommand {

    public MillionDollarsButMessageCommand()
    {
        super();
    }

    @Override
    public String getMessage() {
        return QuestionService.getInstance().getQuestion();
    }
}
