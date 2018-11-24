package com.github.scraniel.milliondollarsbutbot.data;

public class Question {
    private final String MILLION_DOLLARS_PREFIX = "You get a million dollars, but... ";

    String id;
    String question;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Question(String id, String question)
    {
        this.id = id;
        this.question = question;
    }

    public String get()
    {
        return String.format("Question %s: %s%s", id, MILLION_DOLLARS_PREFIX, question);
    }
}
