package com.example.traininglog.ui.base.home;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Workout;

import java.util.List;

public interface HomeView extends BaseView {
    void saveWorkouts(List<Workout> workouts);
}
