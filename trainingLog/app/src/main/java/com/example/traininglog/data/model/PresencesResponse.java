package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PresencesResponse {
    @SerializedName("Presences")
    private List<Presence> presences;

    public PresencesResponse(List<Presence> presences) {
        this.presences = presences;
    }

    public List<Presence> getPresences() {
        return presences;
    }

    public void setPresences(List<Presence> presences) {
        this.presences = presences;
    }
}
