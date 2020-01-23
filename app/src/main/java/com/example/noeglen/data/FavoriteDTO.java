package com.example.noeglen.data;

/**
 * Favorit objektet bliver brugt til at overføre data frem og tilbage til databasen og også gemme i sharedpreferences
 */

public class FavoriteDTO {

    /**
     * @variable CURRENT_TYPE
     * Bliver brugt til at tjekke hvilken type objektet er. Om det er en video favorit, øvelse eller video
     * @variable imageURL
     * Bliver brugt til at loade billede imageview på fragmentet
     * @variable title
     * Bliver brugt til at tjekke hvilken favorit det er senere og også er titlen for selve favoritten
     * @variable bodyORweek
     * Bliver brugt til 2 ting. Om det er Body, så er det brødteksten for artiklen. Om det er week, bliver det brugt til at holde styr på hvilken video det er
     * @variable videoURL
     * Bliver kun brugt hvis det er en video favorit. Det er URL'en der loades i videoafspilleren
     */

    private int CURRENT_TYPE;

    private String iamgeURL;
    private String title;
    private String bodyORweek;
    private String videoURL;

    /** KNOWLEDGE OR EXERCISE FAVORITE OBJECT */
    public FavoriteDTO(int CURRENT_TYPE, String iamgeURL, String title, String bodyORweek) {
        this.CURRENT_TYPE = CURRENT_TYPE;
        this.iamgeURL = iamgeURL;
        this.title = title;
        this.bodyORweek = bodyORweek;
    }

    /** VIDEO FAVORITE OBJECT */
    public FavoriteDTO(int CURRENT_TYPE, String iamgeURL, String title, String bodyORweek, String videoURL) {
        this.CURRENT_TYPE = CURRENT_TYPE;
        this.iamgeURL = iamgeURL;
        this.title = title;
        this.bodyORweek = bodyORweek;
        this.videoURL = videoURL;
    }

    /** GETTERS AND SETTERS */

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
