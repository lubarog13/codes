package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

public class CoachResponse {
    @SerializedName("Coach")
    Coach coach;

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }
}
