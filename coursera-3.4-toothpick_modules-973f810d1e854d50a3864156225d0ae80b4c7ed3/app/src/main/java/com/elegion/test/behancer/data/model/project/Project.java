package com.elegion.test.behancer.data.model.project;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vladislav Falzan.
 */
@Entity
public class Project implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String mName;

    @ColumnInfo(name = "published_on")
    @SerializedName("published_on")
    private long mPublishedOn;

    @SerializedName("covers")
    @Ignore
    private Cover mCover;

    @SerializedName("owners")
    @Ignore
    private List<Owner> mOwners;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    public long getPublishedOn() {
        return mPublishedOn;
    }

    public void setPublishedOn(long publishedOn) {
        mPublishedOn = publishedOn;
    }

    public Cover getCover() {
        return mCover;
    }

    public void setCover(@NonNull Cover cover) {
        mCover = cover;
    }

    public List<Owner> getOwners() {
        return mOwners;
    }

    public void setOwners(@NonNull List<Owner> owners) {
        mOwners = owners;
    }
}
