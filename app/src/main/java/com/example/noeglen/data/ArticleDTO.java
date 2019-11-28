package com.example.noeglen.data;

public class ArticleDTO implements IFavoritesDTO {

    private int dbID;
    private String title;
    private String textBody;
    private String imageName;

    public ArticleDTO(int dbID, String title, String textBody, String imageName) {
        this.dbID = dbID;
        this.title = title;
        this.textBody = textBody;
        this.imageName = imageName;
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

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}