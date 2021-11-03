package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClubResponse {
    @SerializedName("Clubs")
    private List<Club> clubs;

    public ClubResponse(List<Club> clubs) {
        this.clubs = clubs;
    }

    public ClubResponse() {
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }
}
