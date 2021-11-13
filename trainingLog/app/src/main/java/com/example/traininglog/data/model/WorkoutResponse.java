package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkoutResponse {
    @SerializedName("Workouts")
    private List<Workout> workouts;

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    public WorkoutResponse(List<Workout> workouts) {
        this.workouts = workouts;
    }
}
