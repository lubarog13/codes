package com.example.traininglog.ui.base.analysis.coach.trainings;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.GroupAnalysis;
import com.example.traininglog.data.model.TypesResponse;

import java.util.List;

public interface WorkoutAnalysisView extends BaseView {
    void showAnalysisForTypes(TypesResponse response, int month);
    void showWorkoutAnalysis(List<GroupAnalysis.GroupAnalysisItem> workouts, List<GroupAnalysis.GroupAnalysisItem> presences);
}
