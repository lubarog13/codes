package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Workout {
    @SerializedName("id")
    private int id;
    @SerializedName("start_time")
    private Date start_time;
    @SerializedName("end_time")
    private Date end_time;
    @SerializedName("type")
    private String type;
    @SerializedName("other_type")
    private String other_type;
    @SerializedName("is_carried_out")
    private boolean is_carried_out;
    @SerializedName("coach_replace")
    private Coach coach;
    @SerializedName("hall")
    private Hall hall;
    @SerializedName("club")
    private Club club;
    @SerializedName("on_train")
    private int on_train;
    @SerializedName("dont_know")
    private int dont_know;
    @SerializedName("not_on_train")
    private int not_on_train;
    @SerializedName("is_on")
    private Boolean is_on;

    public Workout(int id, Date start_time, Date end_time, String type, String other_type, boolean is_carried_out, Coach coach, Hall hall, Club club, int on_train, int dont_know, int not_on_train, Boolean is_on) {
        this.id = id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.type = type;
        this.other_type = other_type;
        this.is_carried_out = is_carried_out;
        this.coach = coach;
        this.hall = hall;
        this.club = club;
        this.on_train = on_train;
        this.dont_know = dont_know;
        this.not_on_train = not_on_train;
        this.is_on = is_on;
    }

    public Workout(int id, Date start_time, Date end_time, String type, String other_type, boolean is_carried_out, Coach coach, Hall hall, Club club) {
        this.id = id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.type = type;
        this.other_type = other_type;
        this.is_carried_out = is_carried_out;
        this.coach = coach;
        this.hall = hall;
        this.club = club;
    }

    public Workout(int id) {
        this.id = id;
    }

    public Workout() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOther_type() {
        return other_type;
    }

    public void setOther_type(String other_type) {
        this.other_type = other_type;
    }

    public boolean isIs_carried_out() {
        return is_carried_out;
    }

    public void setIs_carried_out(boolean is_carried_out) {
        this.is_carried_out = is_carried_out;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public int getOn_train() {
        return on_train;
    }

    public void setOn_train(int on_train) {
        this.on_train = on_train;
    }

    public int getDont_know() {
        return dont_know;
    }

    public void setDont_know(int dont_know) {
        this.dont_know = dont_know;
    }

    public int getNot_on_train() {
        return not_on_train;
    }

    public void setNot_on_train(int not_on_train) {
        this.not_on_train = not_on_train;
    }

    public boolean Is_on() {
        return is_on;
    }

    public void setIs_on(boolean is_on) {
        this.is_on = is_on;
    }
}
