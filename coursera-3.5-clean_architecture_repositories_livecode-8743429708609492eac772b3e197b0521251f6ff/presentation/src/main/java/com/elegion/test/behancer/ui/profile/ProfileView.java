package com.elegion.test.behancer.ui.profile;

import com.elegion.domain.model.user.User;
import com.elegion.test.behancer.common.BaseView;

public interface ProfileView extends BaseView {
    void showUser(User user);
    String getUsername();
}
