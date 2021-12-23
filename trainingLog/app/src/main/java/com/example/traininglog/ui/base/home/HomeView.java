package com.example.traininglog.ui.base.home;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.FCMDevice;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Presence_W_N;
import com.example.traininglog.data.model.Workout;

import java.util.List;

public interface HomeView extends BaseView {
    void saveWorkouts(List<Workout> workouts);
    void updatePresence(boolean is_attend);
    void showPresences(List<Presence_W_N> presences);
    void showNetworkError();
    void showValues(List<Hall> halls, List<Coach> coaches, List<Club> clubs);
    void updateComplete();
    void saveDevice(FCMDevice fcmDevice);
    void showCoachNetworkError();
    void messageSent();
}
