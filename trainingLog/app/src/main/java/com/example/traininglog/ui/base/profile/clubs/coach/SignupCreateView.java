package com.example.traininglog.ui.base.profile.clubs.coach;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Club;

import java.util.List;

public interface SignupCreateView extends BaseView {
    void showClubs(List<Club> clubs);
    void showUserNotFound();
    void setOk();
}
