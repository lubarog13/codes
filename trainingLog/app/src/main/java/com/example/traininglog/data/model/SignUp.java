package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SignUp {
    @SerializedName("id")
    private int id;
    @SerializedName("club")
    private Club club;
    @SerializedName("user")
    private User user;
    @SerializedName("start_date")
    private Date start_date;
    @SerializedName("end_date")
    private Date end_date;

    public SignUp(int id, Club club, User user, Date start_date, Date end_date) {
        this.id = id;
        this.club = club;
        this.user = user;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public SignUp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
