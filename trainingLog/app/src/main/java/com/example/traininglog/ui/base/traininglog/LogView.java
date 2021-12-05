package com.example.traininglog.ui.base.traininglog;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.Workout;

import java.util.List;

public interface LogView extends BaseView {
    void showWorkouts(List<Workout> workout);
    void showPresences(List<Presence> presences);
}
