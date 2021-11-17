package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

public class Presence_W_N {
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user;
    @SerializedName("workout")
    private int workout;
    @SerializedName("is_attend")
    private Boolean is_attend;
    @SerializedName("reason")
    private String reason;
    @SerializedName("delay")
    private boolean delay;
    @SerializedName("early_ret")
    private boolean early_ret;

    public Presence_W_N(int id) {
        this.id = id;
    }

    public Presence_W_N(int id, User user, int workout, boolean is_attend, String reason, boolean delay, boolean early_ret) {
        this.id = id;
        this.user = user;
        this.workout = workout;
        this.is_attend = is_attend;
        this.reason = reason;
        this.delay = delay;
        this.early_ret = early_ret;
    }

    public Presence_W_N() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getWorkout() {
        return workout;
    }

    public void setWorkout(int workout) {
        this.workout = workout;
    }

    public Boolean isIs_attend() {
        return is_attend;
    }

    public void setIs_attend(Boolean is_attend) {
        this.is_attend = is_attend;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isDelay() {
        return delay;
    }

    public void setDelay(boolean delay) {
        this.delay = delay;
    }

    public boolean isEarly_ret() {
        return early_ret;
    }

    public void setEarly_ret(boolean early_ret) {
        this.early_ret = early_ret;
    }
}
