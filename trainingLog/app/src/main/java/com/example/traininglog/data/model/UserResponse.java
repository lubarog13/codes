package com.example.traininglog.data.model;

import android.view.LayoutInflater;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @SerializedName("Users")
    private List<User> users;

    public UserResponse(List<User> users) {
        this.users = users;
    }

    public UserResponse() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
