package com.example.arogyasetu2;

public class QuestionLibrary {

    private String mQuestions [] = {
            "Are you Experiencing any of the following symptoms??",
            "Have you ever had any of the following??",
            "Have you travelled anywhere internationally in last 28-45 days?",
            "Have you been with someone who has covid-19 virus infection??"
    };


    private String mChoices [][] = {
            {"Cough", "Fever", "Difficulty in Breathing ","loss of senses of test and smell","None of the above"},
            {"Diabetes", "Hypertension", "Lung disorder","heart disease","None of the above"},
            {"Yes", "No","","",""},
            {"Yes", "No", "","",""}
    };


    private String mCorrectAnswers[] = {"None of the above", "None of the above", "No", "No"};

    public String getQuestion(int a) {
        return mQuestions[a];
    }


    public String getChoice1(int a) {
        return mChoices[a][0];
    }


    public String getChoice2(int a) {
        return mChoices[a][1];
    }

    public String getChoice3(int a) {
        return mChoices[a][2];
    }

    public String getChoice4(int a) {
        return mChoices[a][3];
    }

    public String getChoice5(int a) {
        return mChoices[a][4];
    }

    public String getCorrectAnswer(int a) {
        return mCorrectAnswers[a];
    }

}