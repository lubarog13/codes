package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {
    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("second_name")
    private String second_name;
    @SerializedName("email")
    private String email;
    @SerializedName("date_birth")
    private Date date_birth;
    @SerializedName("sex")
    private String sex;
    @SerializedName("is_coach")
    private boolean is_coach;
    @SerializedName("username")
    private String username;

    public User(int id, String first_name, String last_name, String second_name, String email, Date date_birth, String sex, boolean is_coach, String username) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.second_name = second_name;
        this.email = email;
        this.date_birth = date_birth;
        this.sex = sex;
        this.is_coach = is_coach;
        this.username = username;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(Date date_birth) {
        this.date_birth = date_birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean Is_coach() {
        return is_coach;
    }

    public void setIs_coach(boolean is_coach) {
        this.is_coach = is_coach;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
