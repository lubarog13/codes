package com.example.traininglog.ui.base.profile.clubs.coach;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.model.Club;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Workout;
import com.example.traininglog.data.model.WorkoutForEdit;
import com.example.traininglog.utils.ApiUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class WorkoutCreatePresenter extends BasePresenter<WorkoutCreateView> {

    public void getValues() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getClubsForCoach(ApiUtils.coach_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable ->  getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        clubResponse -> getCoaches(clubResponse.getClubs()),
                        getViewState()::showError
                )
        );
    }

    private void getCoaches(List<Club> clubs) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getCoaches()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable ->  getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                coaches -> getHalls(clubs, coaches),
                                getViewState()::showError
                        )
        );
    }

    private void getHalls(List<Club> clubs, List<Coach> coaches) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getHalls()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable ->  getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                halls -> getViewState().showValues(clubs, coaches, halls),
                                getViewState()::showError
                        )
        );
    }

    public void createWorkout(List<WorkoutForEdit> workouts, boolean isOne) {
        for(WorkoutForEdit workout: workouts) {
            mCompositeDisposable.add(
                    ApiUtils.getApiService().createWorkout(workout)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> {
                                        if (isOne) {
                                            getViewState().createdOne();
                                        } else {
                                            getViewState().createdOneOf();
                                        }
                                    },
                                    getViewState()::showError
                            )
            );
        }
    }

}
