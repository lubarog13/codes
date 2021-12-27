package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

public class AuthUser {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("auth_token")
    private String auth_token;
    @SerializedName("re_password")
    private String re_password;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("second_name")
    private String second_name;
    @SerializedName("email")
    private String email;
    @SerializedName("date_birth")
    private String date_birth;
    @SerializedName("sex")
    private String sex;
    @SerializedName("is_coach")
    private boolean is_coach;

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

    public AuthUser(String first_name, String last_name, String second_name, String email, String date_birth, String sex, boolean is_coach) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.second_name = second_name;
        this.email = email;
        this.date_birth = date_birth;
        this.sex = sex;
        this.is_coach = is_coach;
    }




    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
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

    public String getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(String date_birth) {
        this.date_birth = date_birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isIs_coach() {
        return is_coach;
    }

    public void setIs_coach(boolean is_coach) {
        this.is_coach = is_coach;
    }
}
