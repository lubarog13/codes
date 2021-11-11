package com.example.traininglog.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "id",
        childColumns = "user_id"
))
public class Coach {
    @ColumnInfo(name = "id")
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("post")
    @ColumnInfo(name = "post")
    private String post;
    @SerializedName("user")
    @Ignore
    private User user;

    @ColumnInfo(name = "user_id")
    private int userId;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + id +
                ", post='" + post + '\'' +
                ", user=" + user +
                ", userId=" + userId +
                '}';
    }
}
