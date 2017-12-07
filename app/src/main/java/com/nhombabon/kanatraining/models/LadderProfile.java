package com.nhombabon.kanatraining.models;

public class LadderProfile {

    private int image;
    private String username;
    private int score;

    public LadderProfile(int image, String username, int score) {
        this.image = image;
        this.username = username;
        this.score = score;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
