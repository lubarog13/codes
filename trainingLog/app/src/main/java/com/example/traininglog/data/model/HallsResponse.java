package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HallsResponse {
    @SerializedName("Halls")
    private List<Hall> halls;

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }
}
