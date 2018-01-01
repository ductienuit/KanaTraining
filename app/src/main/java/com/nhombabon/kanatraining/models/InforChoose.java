package com.nhombabon.kanatraining.models;

/**
 * Created by DucTien on 31/12/2017.
 */

public class InforChoose {
    private static InforChoose ourInstance = new InforChoose();
    private static int isStateSound=1;
    private static int chooseKana=0;
    private static int preChooseKana=0;
    private static int score=0;

    public static InforChoose getInstance() {

       if(ourInstance==null)
           ourInstance = new InforChoose();
        return ourInstance;
    }

    private InforChoose() {
    }

    public static int getStateSound() {
        return isStateSound;
    }

    public static void setStateSound(int isStateSound) {
        InforChoose.isStateSound = isStateSound;
    }

    public static int getChooseKana() {
        return chooseKana;
    }

    public static void setChooseKana(int chooseKana) {
        InforChoose.chooseKana = chooseKana;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        InforChoose.score = score;
    }
}
