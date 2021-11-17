package com.example.traininglog.ui.base.schedule;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.Presence_W_N;

import java.util.List;

public interface ScheduleView extends BaseView {
    void showPresences(List<Presence> presences);
    void updatePresence(boolean is_attend);
    void showPresence(List<Presence_W_N> presences);
    void showNetworkError();
}
