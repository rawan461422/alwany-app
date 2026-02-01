package com.example.alwanyapp.Domain;

public class Question {
    private int number, answerOne,answerTwo,answerThree,answerFour;
    private String title, question;

    public Question(int number, int answerOne, int answerTwo, int answerThree, int answerFour, String title, String question) {
        this.number = number;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerFour = answerFour;
        this.title = title;
        this.question = question;
    }

    public int getNumber() {
        return number;
    }

    public int getAnswerOne() {
        return answerOne;
    }

    public int getAnswerTwo() {
        return answerTwo;
    }

    public int getAnswerThree() {
        return answerThree;
    }

    public int getAnswerFour() {
        return answerFour;
    }

    public String getTitle() {
        return title;
    }

    public String getQuestion() {
        return question;
    }
}
