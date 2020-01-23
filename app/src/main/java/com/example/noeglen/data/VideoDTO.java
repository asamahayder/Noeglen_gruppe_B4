package com.example.noeglen.data;

/**
 * Objektet der bruges til at hente fra databasen og også loade videor på videosiden. Gemmes også i sharedpreferences
 */

public class VideoDTO {

    /**
     * @variable videoURL
     * Er selve URLen som skal streames i videoafspilleren
     * @variable imageURL
     * Er videons billede der loades ind i listen der vises i et af fragmenterne
     * @variable title
     * Selve titlen af videon
     * @variable week
     * Ugen hvor denne video finder sig i. Bliver brugt til at finde videon i databasen også
     */
    private String videoUrl;
    private String imageUrl;
    private String title;
    private String week;

    public VideoDTO(){}

    public VideoDTO(String title, String videoUrl, String imageUrl, String week) {
        this.videoUrl = videoUrl;
        this.title = title;
        this.imageUrl = imageUrl;
        this.week = week;
    }

    /** GETTERS AND SETTERS */

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
