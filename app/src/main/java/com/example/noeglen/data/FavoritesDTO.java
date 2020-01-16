package com.example.noeglen.data;

import java.util.List;

public class FavoritesDTO {

    private List<KnowledgeDTO> listOfKnowledgeDTOS;
    private List<VideoDTO> listOfVideoDTOS;
    private List<ExerciseDTO> listOfExerciseDTOS;

    public FavoritesDTO(List<KnowledgeDTO> listOfKnowledgeDTOS, List<VideoDTO> listOfVideoDTOS, List<ExerciseDTO> listOfExerciseDTOS) {
        this.listOfKnowledgeDTOS = listOfKnowledgeDTOS;
        this.listOfVideoDTOS = listOfVideoDTOS;
        this.listOfExerciseDTOS = listOfExerciseDTOS;
    }

    /** GETTERS AND SETTERS */

    public List<KnowledgeDTO> getListOfKnowledgeDTOS() {
        return listOfKnowledgeDTOS;
    }

    public void setListOfKnowledgeDTOS(List<KnowledgeDTO> listOfKnowledgeDTOS) {
        this.listOfKnowledgeDTOS = listOfKnowledgeDTOS;
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
