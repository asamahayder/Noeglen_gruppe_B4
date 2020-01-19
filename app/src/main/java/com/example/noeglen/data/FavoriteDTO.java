package com.example.noeglen.data;

public class FavoriteDTO {

    private int CURRENT_TYPE;

    private String iamgeURL;
    private String title;
    private String bodyORweek;
    private String videoURL;

    // KNOWLEDGE
    public FavoriteDTO(int CURRENT_TYPE, String iamgeURL, String title, String bodyORweek) {
        this.CURRENT_TYPE = CURRENT_TYPE;
        this.iamgeURL = iamgeURL;
        this.title = title;
        this.bodyORweek = bodyORweek;
    }

    // VIDEO
    public FavoriteDTO(int CURRENT_TYPE, String iamgeURL, String title, String bodyORweek, String videoURL) {
        this.CURRENT_TYPE = CURRENT_TYPE;
        this.iamgeURL = iamgeURL;
        this.title = title;
        this.bodyORweek = bodyORweek;
        this.videoURL = videoURL;
    }

    public int getCURRENT_TYPE() {
        return CURRENT_TYPE;
    }

    public void setCURRENT_TYPE(int CURRENT_TYPE) {
        this.CURRENT_TYPE = CURRENT_TYPE;
    }

    public String getIamgeURL() {
        return iamgeURL;
    }

    public void setIamgeURL(String iamgeURL) {
        this.iamgeURL = iamgeURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBodyORweek() {
        return bodyORweek;
    }

    public void setBodyORweek(String bodyORweek) {
        this.bodyORweek = bodyORweek;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
