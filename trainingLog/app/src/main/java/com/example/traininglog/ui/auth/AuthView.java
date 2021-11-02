package com.example.traininglog.ui.auth;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.data.model.User;

public interface AuthView extends BaseView {
    void showSuccess( AuthUser user);
    AuthUser getUser();
    void navigateHome();
}
