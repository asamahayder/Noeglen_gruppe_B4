package com.example.noeglen.data;

/**
 * Dagbog objektet som bliver brugt til at gemme data i SharedPreferences.
 */

public class DiaryDTO implements Comparable<DiaryDTO> {

    /**
     * @variable smiley
     * Bliver brugt til at holde styr på hvilken smiley man valgte
     * @variable answers
     * Bliver brugt til at gemme svarene man indtastede i fragmentet
     * @variable questions
     * Bliver brugt til at skrive spørgsmålene igen når man henter fra sharedpreferences
     * @variable date
     * Bliver brugt til at se hvilken dato man gemte dagbog noten og senere hen tjekke i calenderen
     */

    private int smiley;
    private String [] answers, questions;
    private String date;

    public DiaryDTO(int smiley) {
        this.smiley = smiley;
    }

    public DiaryDTO(int smiley, String[] answers, String[] questions, String date) {
        this.smiley = smiley;
        this.answers = answers;
        this.questions = questions;
        this.date = date;
    }

    /** GETTERS AND SETTERS */

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
