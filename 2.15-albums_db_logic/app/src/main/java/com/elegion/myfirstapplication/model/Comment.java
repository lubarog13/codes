package com.elegion.myfirstapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.sql.Timestamp;

import okio.Utf8;

public class Comment {
    @SerializedName("id")
    private int mId;
    @SerializedName("text")
    private String mText;
    @SerializedName("author")
    private String mAuthor;
    @SerializedName("timestamp")
    private Date mTimestamp;
    @SerializedName("album_id")
    private int mAlbumId;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public Date getmTimestamp() {
        return mTimestamp;
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

    public void setmTimestamp(Date mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    public int getmAlbumId() {
        return mAlbumId;
    }

    public void setmAlbumId(int mAlbumId) {
        this.mAlbumId = mAlbumId;
    }
}
