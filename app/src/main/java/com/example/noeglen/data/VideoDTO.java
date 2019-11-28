package com.example.noeglen.data;

public class VideoDTO implements IFavoritesDTO {

    private String videoID;
    private boolean watched;
    private String title;

    public VideoDTO(){}

    public VideoDTO(String title, String videoID, boolean watched) {
        this.videoID = videoID;
        this.watched = watched;
        this.title = title;
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

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

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
}
