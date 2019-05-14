package com.github.scraniel.robotosensei;

import com.github.scraniel.robotosensei.mdb.QuestionService;
import org.junit.Assert;
import org.junit.Test;

public class QuestionServiceTest {

    @Test
    public void QuestionService_GetPositiveResponseCountTest_Nulls() {
        QuestionService.getInstance().init(null, null);

        Assert.assertEquals(0, QuestionService.getInstance().getPositiveResponseCount(0));
    }
}
