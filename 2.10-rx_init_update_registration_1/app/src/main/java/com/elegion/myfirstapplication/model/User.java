package com.elegion.myfirstapplication.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    @SerializedName("data")
    private DataBean mData;

    public User.DataBean getData() {
        return mData;
    }

    public void setData(User.DataBean data) {
        mData = data;
    }

    public static class DataBean implements Serializable {
        public DataBean(String mEmail, String mName, String mPassword) {
            this.mEmail = mEmail;
            this.mName = mName;
            this.mPassword = mPassword;
        }

        @SerializedName("email")
        private String mEmail;
        @SerializedName("name")
        private String mName;
        @SerializedName("password")
        private String mPassword;

        private boolean mHasSuccessEmail;
        @Override
        public String toString() {
            return "User{" +
                    "mEmail='" + mEmail + '\'' +
                    ", mName='" + mName + '\'' +
                    ", mPassword='" + mPassword + '\'' +
                    ", mHasSuccessEmail=" + mHasSuccessEmail +
                    '}';
        }

        public String getEmail() {
            return mEmail;
        }

        public void setEmail(String email) {
            mEmail = email;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getPassword() {
            return mPassword;
        }

        public void setPassword(String password) {
            mPassword = password;
        }

        public boolean hasSuccessEmail() {
            return mHasSuccessEmail;
        }

        public void setHasSuccessEmail(boolean hasSuccessEmail) {
            mHasSuccessEmail = hasSuccessEmail;
        }
    }
}
