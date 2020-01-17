package com.example.noeglen.data;

public class KnowledgeDTO {

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
