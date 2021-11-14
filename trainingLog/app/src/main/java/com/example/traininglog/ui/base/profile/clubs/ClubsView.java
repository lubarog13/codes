package com.example.traininglog.ui.base.profile.clubs;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.SignUp;
import com.example.traininglog.data.model.User;

import java.util.List;

public interface ClubsView extends BaseView {
     void setSignUps(List<SignUp> signUps);
     void setUsers(List<User> users, int signup_id);
}