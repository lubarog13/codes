package com.elegion.test.behancer.data.model.project;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vladislav Falzan.
 */
@Entity(foreignKeys = @ForeignKey(
        entity = Project.class,
        parentColumns = "id",
        childColumns = "project_id"
))
public class Cover implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "photo_url")
    @SerializedName("202")
    private String mPhotoUrl;

    @ColumnInfo(name = "project_id")
    private int mProjectId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(@NonNull String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public int getProjectId() {
        return mProjectId;
    }

    public void setProjectId(int projectId) {
        mProjectId = projectId;
    }
}
