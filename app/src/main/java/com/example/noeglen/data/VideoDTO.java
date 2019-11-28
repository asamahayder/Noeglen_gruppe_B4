package com.example.noeglen.data;

public class VideoDTO implements IFavoritesDTO {

    private int dbID;
    private boolean watched;
    private String title;
    private String progression;

    public VideoDTO(int dbID, boolean watched, String title, String progression) {
        this.dbID = dbID;
        this.watched = watched;
        this.title = title;
        this.progression = progression;
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

    public String getProgression() {
        return progression;
    }

    public void setProgression(String progression) {
        this.progression = progression;
    }
}
