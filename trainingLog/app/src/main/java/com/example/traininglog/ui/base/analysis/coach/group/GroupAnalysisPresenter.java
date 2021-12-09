package com.example.traininglog.ui.base.analysis.coach.group;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.GroupAnalysis;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class GroupAnalysisPresenter extends BasePresenter<GroupAnalysisView> {
    public void getClubs() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getClubsForCoach(ApiUtils.coach_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable ->  getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        clubResponse -> getViewState().showClubs(clubResponse.getClubs()),
                        getViewState()::showError
                )
        );
    }

    public void getAnalysisForClubs(String type) {
        GroupAnalysis.DayType dayType = new GroupAnalysis.DayType();
        dayType.setDay(type);
        mCompositeDisposable.add(
                ApiUtils.getApiService().getGroupPresence(ApiUtils.coach_id, dayType)
                .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable ->  getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        groupAnalysis -> getViewState().showAnalysis(groupAnalysis.getItems()),
                        getViewState()::showError
                )
        );
    }

    public void getWorkoutCountForGroup(Club club) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getCountForTypesForGroup(ApiUtils.coach_id, club.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable ->  getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                typesResponse -> getViewState().showCountForTypes(club.getGroup(), typesResponse),
                                getViewState()::showError
                        )
        );
    }
}
