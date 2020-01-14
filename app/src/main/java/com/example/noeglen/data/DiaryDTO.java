package com.example.noeglen.data;

public class DiaryDTO {

    private int smiley;
    private String [] answers;
    private String date;

    public DiaryDTO(int smiley, String[] answers, String date) {
        this.smiley = smiley;
        this.answers = answers;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
