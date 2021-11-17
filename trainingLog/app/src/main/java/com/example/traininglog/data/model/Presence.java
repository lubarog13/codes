package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class Presence {
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user;
    @SerializedName("workout")
    private Workout workout;
    @SerializedName("is_attend")
    private Boolean is_attend;
    @SerializedName("reason")
    private String reason;
    @SerializedName("delay")
    private boolean delay;
    @SerializedName("early_ret")
    private boolean early_ret;

    public Presence(int id) {
        this.id = id;
    }

    public Presence(int id, User user, Workout workout, boolean is_attend, String reason, boolean delay, boolean early_ret) {
        this.id = id;
        this.user = user;
        this.workout = workout;
        this.is_attend = is_attend;
        this.reason = reason;
        this.delay = delay;
        this.early_ret = early_ret;
    }

    public Presence() {
    }

    public boolean isInDay(Calendar calendar) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(this.workout.getStart_time());
        return calendar.get(Calendar.DATE) == calendar1.get(Calendar.DATE);
    }

    public Presence(Boolean is_attend) {this.is_attend = is_attend;}

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

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
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
