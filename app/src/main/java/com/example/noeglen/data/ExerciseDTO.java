package com.example.noeglen.data;

public class ExerciseDTO {

    private String title;
    private String image;

    public ExerciseDTO() {}

    public ExerciseDTO(String title, String image) {
        this.title = title;
        this.image = image;
    }

    /** GETTERS AND SETTERS */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
