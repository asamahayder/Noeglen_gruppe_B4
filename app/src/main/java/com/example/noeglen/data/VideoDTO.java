package com.example.noeglen.data;

public class VideoDTO implements IFavoritesDTO {

    private String videoUrl;
    private String imageUrl;
    private String title;

    public VideoDTO(){}

    public VideoDTO(String title, String videoUrl, String imageUrl, boolean watched) {
        this.videoUrl = videoUrl;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    //FIXME

    @Override
    public boolean addFav(int dbID) {
        return false;
    }

    //FIXME

    @Override
    public boolean remFav(int dbID) {
        return false;
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
