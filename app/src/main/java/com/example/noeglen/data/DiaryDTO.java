package com.example.noeglen.data;

import com.example.noeglen.logic.CurrentDate;

public class DiaryDTO {
    private int smiley;
    private String [] questions;
    CurrentDate date;

    public DiaryDTO(int smilie, String[] questions, CurrentDate date) {
        this.smiley = smilie;
        this.questions = questions;
        this.date = date;
    }

    public int getSmiley() {
        return smiley;
    }

    public void setSmiley(int smiley) {
        this.smiley = smiley;
    }

    public String[] getQuestions() {
        return questions;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public CurrentDate getDate() {
        return date;
    }

    public void setDate(CurrentDate date) {
        this.date = date;
    }
}
