package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PresenceResponse {
    @SerializedName("Presences")
    private List<Presence_W_N> presences;

    public List<Presence_W_N> getPresences() {
        return presences;
    }

    public void setPresences(List<Presence_W_N> presences) {
        this.presences = presences;
    }
}
