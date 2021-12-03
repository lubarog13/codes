package com.example.traininglog.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class WorkoutForEdit{
    @SerializedName("id")
    private int id;
    @SerializedName("start_time")
    private String start_time;
    @SerializedName("end_time")
    private String end_time;
    @SerializedName("type")
    private String type;
    @SerializedName("other_type")
    private String other_type;
    @SerializedName("is_carried_out")
    private boolean is_carried_out;
    @SerializedName("coach_replace")
    private Integer coach;
    @SerializedName("hall")
    private int hall;
    @SerializedName("club")
    private int club;

    public WorkoutForEdit(int id, String start_time, String end_time, String type, String other_type, boolean is_carried_out, Integer coach, int hall, int club) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
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

    public Integer getCoach() {
        return coach;
    }

    public void setCoach(Integer coach) {
        this.coach = coach;
    }

    public int getHall() {
        return hall;
    }

    public void setHall(int hall) {
        this.hall = hall;
    }

    public int getClub() {
        return club;
    }

    public void setClub(int club) {
        this.club = club;
    }

    @Override
    public String toString() {
        return "WorkoutForEdit{" +
                "id=" + id +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", type='" + type + '\'' +
                ", other_type='" + other_type + '\'' +
                ", is_carried_out=" + is_carried_out +
                ", coach=" + coach +
                ", hall=" + hall +
                ", club=" + club +
                '}';
    }
}
