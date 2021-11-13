package com.elegion.myfirstapplication.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.sql.Timestamp;

import okio.Utf8;
@Entity

public class Comment {
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo( name = "id")
    private int mId;
    @SerializedName("text")
    @ColumnInfo(name = "text")
    private String mText;
    @SerializedName("author")
    @ColumnInfo(name = "author")
    private String mAuthor;
    @SerializedName("timestamp")
    @ColumnInfo(name = "dispatch_time")
    private Date mTimestamp;
    @ColumnInfo(name = "album_id")
    @SerializedName("album_id")
    private int mAlbumId;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public Date getTimestamp() {
        return mTimestamp;
    }

    public Comment(int mId, String mText, String mAuthor, Date mTimestamp, int mAlbumId) {
        this.mId = mId;
        this.mText = mText;
        this.mAuthor = mAuthor;
        this.mTimestamp = mTimestamp;
        this.mAlbumId = mAlbumId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "mId=" + mId +
                ", mText='" + mText + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mTimestamp=" + mTimestamp +
                ", mAlbumId=" + mAlbumId +
                '}';
    }

    public void setTimestamp(Date mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(int mAlbumId) {
        this.mAlbumId = mAlbumId;
    }
}
