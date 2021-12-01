package com.example.traininglog.ui.base.settings;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.User;

public interface SettingsView extends BaseView {
    void showUser(User user);
}
