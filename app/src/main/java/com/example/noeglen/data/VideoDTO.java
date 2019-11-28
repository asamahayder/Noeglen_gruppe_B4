package com.example.noeglen.data;

public class VideoDTO extends FavoritesDTO {

    private boolean watched;
    private String title;
    private int number;
    private String progression;

    public VideoDTO(boolean watched, String title, int number, String progression) {
        this.watched = watched;
        this.title = title;
        this.number = number;
        this.progression = progression;
    }

    /** GETTERS AND SETTERS */

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getProgression() {
        return progression;
    }

    public void setProgression(String progression) {
        this.progression = progression;
    }
}
