package com.example.traininglog.ui.base.profile.clubs.coach;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Building;

import java.util.List;

public interface ClubCreateView extends BaseView {
    void showBuildings(List<Building> buildings);
    void setOk();
}
