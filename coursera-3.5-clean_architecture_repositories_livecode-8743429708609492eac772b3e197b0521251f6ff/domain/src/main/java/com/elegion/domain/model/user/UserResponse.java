package com.elegion.domain.model.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vladislav Falzan.
 */

public class UserResponse {

    @SerializedName("user")
    private User mUser;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
