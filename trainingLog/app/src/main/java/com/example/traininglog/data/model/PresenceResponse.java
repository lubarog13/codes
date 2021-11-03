package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PresenceResponse {
    @SerializedName("Presences")
    private List<Presence> presences;

    public List<Presence> getPresences() {
        return presences;
    }

    public void setPresences(List<Presence> presences) {
        this.presences = presences;
    }
}
