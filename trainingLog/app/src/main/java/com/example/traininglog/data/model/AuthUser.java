package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

public class AuthUser {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("auth_token")
    private String auth_token;

    public AuthUser() {
    }

    public AuthUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthUser(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
}
