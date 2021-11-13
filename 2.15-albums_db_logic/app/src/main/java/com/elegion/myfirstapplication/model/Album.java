package com.elegion.myfirstapplication.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by marat.taychinov
 */
@Entity
public class Album implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String mName;

    @ColumnInfo(name = "release")
    @SerializedName("release_date")
    private String mReleaseDate;

    @SerializedName("songs")
    @Ignore//этот список вы должны сохранять руками, по примеру Азрета, когда получаете объект альбома с сервера на экране отображения альбома
    private List<Song> mSongs;

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

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public List<Song> getSongs() {
        return mSongs;
    }

    @Override
    public String toString() {
        return "Album{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                ", mSongs=" + mSongs +
                '}';
    }

    public void setSongs(List<Song> songs) {
        mSongs = songs;
    }
}
