package com.example.traininglog.ui.base.profile.clubs.all_clubs;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Building;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.SignUp;

import java.util.List;

public interface AllClubsView extends BaseView {
    void showClubs(List<Club> clubs);
    void showCoaches(List<Coach> coaches);
    void showBuildings(List<Building> buildings);
}
