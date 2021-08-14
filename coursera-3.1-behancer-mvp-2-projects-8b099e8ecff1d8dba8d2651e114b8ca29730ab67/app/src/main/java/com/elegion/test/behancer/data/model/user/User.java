package com.elegion.test.behancer.data.model.user;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vladislav Falzan.
 */
@Entity
public class User {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = "username")
    @SerializedName("username")
    private String mUsername;

    @ColumnInfo(name = "location")
    @SerializedName("location")
    private String mLocation;

    @ColumnInfo(name = "created_on")
    @SerializedName("created_on")
    private long mCreatedOn;

    @SerializedName("images")
    @Ignore
    private Image mImage;

    @ColumnInfo(name = "display_name")
    @SerializedName("display_name")
    private String mDisplayName;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(@NonNull String username) {
        mUsername = username;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(@NonNull String location) {
        mLocation = location;
    }

    public long getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(long createdOn) {
        mCreatedOn = createdOn;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(@NonNull Image image) {
        mImage = image;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(@NonNull String displayName) {
        mDisplayName = displayName;
    }
}
