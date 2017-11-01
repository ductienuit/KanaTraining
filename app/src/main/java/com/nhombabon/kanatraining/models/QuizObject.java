package com.nhombabon.kanatraining.models;


public class QuizObject {

    private String imagePath;
    private String quizName;

    public QuizObject(String imagePath, String quizName) {
        this.imagePath = imagePath;
        this.quizName = quizName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getQuizName() {
        return quizName;
    }
}
