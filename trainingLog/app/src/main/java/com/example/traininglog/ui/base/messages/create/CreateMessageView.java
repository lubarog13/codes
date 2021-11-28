package com.example.traininglog.ui.base.messages.create;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.User;

import java.util.List;

public interface CreateMessageView extends BaseView {
    void showUsers(List<User> users);
    void saveMessage();
}
