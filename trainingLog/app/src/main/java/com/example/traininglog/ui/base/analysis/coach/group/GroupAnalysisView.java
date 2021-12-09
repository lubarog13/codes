package com.example.traininglog.ui.base.analysis.coach.group;

import android.widget.LinearLayout;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.GroupAnalysis;
import com.example.traininglog.data.model.TypesResponse;

import org.eazegraph.lib.charts.BarChart;

import java.util.List;

public interface GroupAnalysisView extends BaseView {
    void showClubs(List<Club> clubs);
    void showAnalysis(List<GroupAnalysis.GroupAnalysisItem> items);
    void showCountForTypes(String name, TypesResponse responses);
}
