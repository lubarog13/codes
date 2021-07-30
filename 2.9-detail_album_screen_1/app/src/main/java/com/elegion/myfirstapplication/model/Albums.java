package com.elegion.myfirstapplication.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by marat.taychinov
 */

public class Albums {
    @SerializedName("data")
    private List<DataBean> mData;

    public List<DataBean> getData() {
        return mData;
    }

    public void setData(List<DataBean> data) {
        mData = data;
    }

    public static class DataBean implements Serializable {
        @SerializedName("id")
        private int mId;
        @SerializedName("name")
        private String mName;
        @SerializedName("songs_count")
        private int mSongsCount;
        @SerializedName("release_date")
        private String mReleaseDate;

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

        public int getSongsCount() {
            return mSongsCount;
        }

        public void setSongsCount(int songsCount) {
            mSongsCount = songsCount;
        }

        public String getReleaseDate() {
            return mReleaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            mReleaseDate = releaseDate;
        }
    }
}
