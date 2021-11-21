package com.example.traininglog.ui.base.profile.buildings;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Building;

import java.util.List;

public interface BuildingsView extends BaseView {
    void showBuildings(List<Building> buildings);
}
