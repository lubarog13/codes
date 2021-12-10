package com.example.traininglog.ui.base.analysis.coach.trainings;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.model.GroupAnalysis;
import com.example.traininglog.utils.ApiUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class WorkoutAnalysisPresenter extends BasePresenter<WorkoutAnalysisView> {

    public void getWorkoutCount() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getCountForTypes(ApiUtils.user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        response -> getViewState().showAnalysisForTypes(response, -1),
                        getViewState()::showError
                )
        );
    }

    public void getWorkoutForTypes(String month) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getWorkoutCountForMonth(ApiUtils.coach_id, new GroupAnalysis.DayType(month))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                groupAnalysis -> getPresencesForTypes(month, groupAnalysis.getItems()),
                                getViewState()::showError
                        )
        );
    }

    private void getPresencesForTypes(String month, List<GroupAnalysis.GroupAnalysisItem> items) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getPresenceCountForMonth(ApiUtils.coach_id, new GroupAnalysis.DayType(month))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                groupAnalysis -> getViewState().showWorkoutAnalysis(items, groupAnalysis.getItems()),
                                getViewState()::showError
                        )
        );
    }

    public void getAnalysisForMonthsForTypes(int user_id, int month) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getCountForTypesInMonth(user_id, month)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                typesResponse -> getViewState().showAnalysisForTypes(typesResponse, month),
                                getViewState()::showError
                        )
        );
    }
}
