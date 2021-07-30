package com.elegion.myfirstapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by marat.taychinov
 */

public class Songs {
    @SerializedName("data")
    private List<DataBean> mData;

    public List<DataBean> getData() {
        return mData;
    }

    public void setData(List<DataBean> data) {
        mData = data;
    }

    public static class DataBean {
        @SerializedName("id")
        private int mId;
        @SerializedName("name")
        private String mName;
        @SerializedName("duration")
        private String mDuration;

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

        public void setDuration(String duration) {
            mDuration = duration;
        }
    }
}
