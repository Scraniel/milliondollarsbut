package com.github.scraniel.milliondollarsbutbot.data;

public class QuestionResponse {

    private boolean answer;

    /*
     * Old questions are taken from the actual show and thus are in one part (key will be as well)
     * New questions are in one part, prefix and postfix. ID will simply be prefixID + postfixID
     */
    private String questionId;

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public QuestionResponse(boolean answer, String questionId) {
        this.answer = answer;
        this.questionId = questionId;
    }
}
