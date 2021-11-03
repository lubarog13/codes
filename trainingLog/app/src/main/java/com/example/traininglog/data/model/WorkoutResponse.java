package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkoutResponse {
    @SerializedName("Workouts")
    private List<Workout> workouts;
}
