package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

public class Coach {
    @SerializedName("id")
    private int id;
    @SerializedName("post")
    private String post;
    @SerializedName("user")
    private User user;

    public Coach(int id) {
        this.id = id;
    }

    public Coach(int id, String post, User user) {
        this.id = id;
        this.post = post;
        this.user = user;
    }

    public Coach() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
