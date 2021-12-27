package com.example.traininglog.ui.base.schedule;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.Presence_W_N;
import com.example.traininglog.data.model.Workout;

import java.util.List;

public interface ScheduleView extends BaseView {
    void showPresences(List<Presence> presences);
    void updatePresence(boolean is_attend);
    void showPresence(List<Presence_W_N> presences);
    void showNetworkError();
    void showWorkouts(List<Workout> workouts);
    void updateComplete();
    void showValues(List<Hall> halls, List<Coach> coaches, List<Club> clubs);
    void showCoachNetworkError();
    void messageSent();
}
