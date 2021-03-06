package com.example.traininglog.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class User {
    @ColumnInfo(name = "id")
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    @ColumnInfo(name = "first_name")
    private String first_name;
    @SerializedName("last_name")
    @ColumnInfo(name = "last_name")
    private String last_name;
    @SerializedName("second_name")
    @ColumnInfo(name = "second_name")
    private String second_name;
    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String email;
    @SerializedName("date_birth")
    @Ignore
    private Date date_birth;
    @SerializedName("sex")
    @ColumnInfo(name = "sex")
    private String sex;
    @SerializedName("is_coach")
    @ColumnInfo(name = "is_coach")
    private boolean is_coach;
    @SerializedName("username")
    @ColumnInfo(name = "username")
    private String username;

    @SerializedName("password")
    @Ignore
    private String password;
    @SerializedName("re_password")
    @Ignore
    private String re_password;

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

    public User(int id){
        this.id = id;
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

    public boolean getIs_coach() {
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

    public User(String first_name, String last_name, String second_name, String email, Date date_birth, String sex, boolean is_coach, String username, String password, String re_password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.second_name = second_name;
        this.email = email;
        this.date_birth = date_birth;
        this.sex = sex;
        this.is_coach = is_coach;
        this.username = username;
        this.password = password;
        this.re_password = re_password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }

    public User(String first_name, String last_name, String second_name, String email, Date date_birth, String sex, boolean is_coach) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.second_name = second_name;
        this.email = email;
        this.date_birth = date_birth;
        this.sex = sex;
        this.is_coach = is_coach;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", email='" + email + '\'' +
                ", date_birth=" + date_birth +
                ", sex='" + sex + '\'' +
                ", is_coach=" + is_coach +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", re_password='" + re_password + '\'' +
                '}';
    }
}
