package com.example.traininglog.ui.base.analysis.coach.users;

import android.text.Layout;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.User;

import java.util.List;

public interface UserButtonView  extends BaseView {
    void showClubs(List<Club> clubs);
    void showUsers(List<User> users);
}
