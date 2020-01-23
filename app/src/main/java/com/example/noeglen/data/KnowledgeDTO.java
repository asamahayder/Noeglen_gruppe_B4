package com.example.noeglen.data;

/**
 * Objektet der bliver hentet fra databasen og senere hen gemt i sharedpreferences og i favoritter
 */

public class KnowledgeDTO {

    /**
     * @variable title
     * Bliver brugt til at holde styr på hvilken artikel det er og er selve titlen for artiklen
     * @variable body
     * Er brødteskten i artiklen
     * @variable image
     * Er billede URLen og bliver brugt til at loade billede imageview senere hen
     */

    private String title;
    private String body;
    private String image;

    public KnowledgeDTO() {
    }

    public KnowledgeDTO(String title, String body, String image) {
        this.title = title;
        this.body = body;
        this.image = image;
    }

    /** GETTERS AND SETTERS */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
