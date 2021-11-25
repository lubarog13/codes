package com.example.traininglog.ui.base.analysis;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.MonthsResponse;
import com.example.traininglog.data.model.TypesResponse;

public interface AnalysisView extends BaseView {
    void showAnalysisForTypes(TypesResponse typesResponse, int month);
    void showAnalysisForMonths(MonthsResponse monthsResponse);
}
