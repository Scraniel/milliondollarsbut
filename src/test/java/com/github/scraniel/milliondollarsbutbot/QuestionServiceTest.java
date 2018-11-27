package com.github.scraniel.milliondollarsbutbot;

import com.github.scraniel.milliondollarsbutbot.commands.MillionDollarsButMessageCommand;
import org.junit.Assert;
import org.junit.Test;

public class QuestionServiceTest {

    @Test
    public void QuestionService_GetPositiveResponseCountTest_Nulls() {
        QuestionService.getInstance().init(null, null);

        Assert.assertEquals(0, QuestionService.getInstance().getPositiveResponseCount(0));
    }
}
