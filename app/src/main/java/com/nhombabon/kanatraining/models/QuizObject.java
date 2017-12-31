package com.nhombabon.kanatraining.models;


public class QuizObject {

    private int imagePath;
    private String quizName;

    public QuizObject(int imagePath, String quizName) {
        this.imagePath = imagePath;
        this.quizName = quizName;
    }

    public int getImagePath() {
        return imagePath;
    }

    public String getQuizName() {
        return quizName;
    }
}
