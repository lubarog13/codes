package com.example.traininglog.ui.base.profile.clubs;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.SignUp;
import com.example.traininglog.data.model.User;

import java.util.List;

public interface ClubsView extends BaseView {
     void setSignUps(List<SignUp> signUps);
     void setClubs(List<Club> clubs);
     void setUsers(List<User> users, int signup_id);
     void showCreatingError(Throwable throwable);
     void updateSignUps(SignUp signUp);
     void showSuccess();
}
