package com.example.noeglen.data;

public class ExerciseDTO implements IFavoritesDTO {

    private int dbID;
    private String title;
    private boolean animated;
    private long animationIn;
    private long animationOut;

    public ExerciseDTO(int dbID, String title, boolean animated, long animationIn, long animationOut) {
        this.dbID = dbID;
        this.title = title;
        this.animated = animated;
        this.animationIn = animationIn;
        this.animationOut = animationOut;
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

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public long getAnimationIn() {
        return animationIn;
    }

    public void setAnimationIn(long animationIn) {
        this.animationIn = animationIn;
    }

    public long getAnimationOut() {
        return animationOut;
    }

    public void setAnimationOut(long animationOut) {
        this.animationOut = animationOut;
    }
}
