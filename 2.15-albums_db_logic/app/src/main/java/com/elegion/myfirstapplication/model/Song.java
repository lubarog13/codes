package com.elegion.myfirstapplication.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import io.reactivex.annotations.Nullable;

/**
 * Created by marat.taychinov
 */
@Entity
public class Song {
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;
    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String mName;
    @SerializedName("duration")
    @ColumnInfo(name = "duration")
    private String mDuration;


    public int getAlbumId() {
        return albumId;
    }

    @ColumnInfo(name = "albumId")
    private int albumId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDuration() {
        return mDuration;
    }
    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }
}
