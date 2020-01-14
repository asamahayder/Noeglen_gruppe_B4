package com.example.noeglen.data;

import java.util.List;

public class FavoritesDTO {

    private List<ArticleDTO> listOfArticleDTOS;
    private List<VideoDTO> listOfVideoDTOS;
    private List<ExerciseDTO> listOfExerciseDTOS;

    public FavoritesDTO(List<ArticleDTO> listOfArticleDTOS, List<VideoDTO> listOfVideoDTOS, List<ExerciseDTO> listOfExerciseDTOS) {
        this.listOfArticleDTOS = listOfArticleDTOS;
        this.listOfVideoDTOS = listOfVideoDTOS;
        this.listOfExerciseDTOS = listOfExerciseDTOS;
    }

    /** GETTERS AND SETTERS */

    public List<ArticleDTO> getListOfArticleDTOS() {
        return listOfArticleDTOS;
    }

    public void setListOfArticleDTOS(List<ArticleDTO> listOfArticleDTOS) {
        this.listOfArticleDTOS = listOfArticleDTOS;
    }

    public List<VideoDTO> getListOfVideoDTOS() {
        return listOfVideoDTOS;
    }

    public void setListOfVideoDTOS(List<VideoDTO> listOfVideoDTOS) {
        this.listOfVideoDTOS = listOfVideoDTOS;
    }

    public List<ExerciseDTO> getListOfExerciseDTOS() {
        return listOfExerciseDTOS;
    }

    public void setListOfExerciseDTOS(List<ExerciseDTO> listOfExerciseDTOS) {
        this.listOfExerciseDTOS = listOfExerciseDTOS;
    }

}
