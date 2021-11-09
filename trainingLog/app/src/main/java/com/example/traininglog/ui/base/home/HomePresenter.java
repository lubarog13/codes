package com.example.traininglog.ui.base.home;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class HomePresenter extends BasePresenter<HomeView> {

    public void getWorkouts() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getWeekWorkouts(ApiUtils.user_id)
                .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                workoutResponse -> getViewState().saveWorkouts(workoutResponse.getWorkouts()),
                                throwable -> getViewState().showError(throwable)
                        )
        );
    }

    public void setPresence(int workout_id) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().updatePresenceForWorkout(ApiUtils.user_id, workout_id, new Presence(true))
                .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        () -> {},
                        throwable -> getViewState().showError(throwable)
                )
        );
    }

    public void resetPresence(int workout_id) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().updatePresenceForWorkout(ApiUtils.user_id, workout_id, new Presence(false))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                () -> {},
                                throwable -> getViewState().showError(throwable)
                        )
        );
    }


    public void getPresences(int workout_id) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getPresencesForWorkout(workout_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        presenceResponse -> getViewState().showPresences(presenceResponse.getPresences()),
                        throwable -> getViewState().showError(throwable)
                )
        );
    }

}
