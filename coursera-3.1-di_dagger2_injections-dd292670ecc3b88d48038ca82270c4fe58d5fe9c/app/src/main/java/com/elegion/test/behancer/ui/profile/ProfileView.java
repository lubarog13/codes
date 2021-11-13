package com.elegion.test.behancer.ui.profile;

import com.elegion.test.behancer.common.BaseView;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.data.model.user.UserResponse;

public interface ProfileView extends BaseView {
    void showUser(UserResponse response);
}
