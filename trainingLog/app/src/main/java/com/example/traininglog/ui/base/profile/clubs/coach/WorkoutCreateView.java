package com.example.traininglog.ui.base.profile.clubs.coach;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;

import java.util.List;

public interface WorkoutCreateView extends BaseView {
    void showValues(List<Club> clubs, List<Coach> coaches, List<Hall> halls);
    void createdOne();
    void createdOneOf();
}
