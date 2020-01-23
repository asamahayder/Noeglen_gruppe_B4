package com.example.noeglen.data;

/**
 * Øvelse objektet bliver brugt til at gemme data i sharedpreferences
 */

public class ExerciseDTO {

    /**
     * @variable title
     * Bliver brugt til at gemme titlen i sharedpreferences og hente den senere hen
     * @variable desc
     * Bliver brugt til at gemme i sharedpreferences ligesom titel
     * @variable image
     * Bliver brugt til at loade en specifik URL og gemt i sharedpreferences
     */
    private String title;
    private String desc;
    private String image;

    public ExerciseDTO() {}

    public ExerciseDTO(String title, String desc, String image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    /** GETTERS AND SETTERS */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
