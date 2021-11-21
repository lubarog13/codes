package com.example.traininglog.ui.base.profile.coaches;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Coach;

import java.util.List;

public interface CoachView extends BaseView {
    void showCoaches(List<Coach> coaches);
}
