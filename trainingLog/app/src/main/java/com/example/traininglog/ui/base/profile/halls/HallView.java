package com.example.traininglog.ui.base.profile.halls;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Hall;

import java.util.List;

public interface HallView extends BaseView {
    void showHalls(List<Hall> halls);
}
