package com.example.noeglen.data;

public class DiaryDTO implements Comparable<DiaryDTO> {

    private int smiley;
    private String [] answers, questions;
    private String date;

    public DiaryDTO(int smiley, String[] answers, String[] questions, String date) {
        this.smiley = smiley;
        this.answers = answers;
        this.questions = questions;
        this.date = date;
    }

    public int getSmiley() {
        return smiley;
    }

    public void setSmiley(int smiley) {
        this.smiley = smiley;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String[] getQuestions() {
        return questions;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(DiaryDTO o) {
        return getDate().compareTo(o.getDate());
    }
}
