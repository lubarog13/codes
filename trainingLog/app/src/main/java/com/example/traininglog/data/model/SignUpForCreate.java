package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SignUpForCreate {
    @SerializedName("identifier")
    private String identifier;
    @SerializedName("user")
    private int user;
    @SerializedName("start_date")
    private Date start_date;
    @SerializedName("end_date")
    private Date end_date;

    public SignUpForCreate(String identifier, int user, Date start_date, Date end_date) {
        this.identifier = identifier;
        this.user = user;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public SignUpForCreate() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
