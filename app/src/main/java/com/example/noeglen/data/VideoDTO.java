package com.example.noeglen.data;

public class VideoDTO {

    private String videoUrl;
    private String imageUrl;
    private String title;

    public VideoDTO(){}

    public VideoDTO(String title, String videoUrl, String imageUrl, boolean watched) {
        this.videoUrl = videoUrl;
        this.title = title;
        this.imageUrl = imageUrl;
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
}
