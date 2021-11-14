package com.example.traininglog.ui.base.home;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class HomePresenter extends BasePresenter<HomeView> {
    private final Storage mStorage;
    private boolean hasError = false;

    public HomePresenter(Storage mStorage) {
        this.mStorage = mStorage;
    }

    public void getWorkouts() {
        HomeActivity.is_firstOpened = false;
        mCompositeDisposable.add(
                ApiUtils.getApiService().getWeekWorkouts(ApiUtils.user_id)
                .subscribeOn(Schedulers.io())
                        .doOnSuccess(response -> {
                            mStorage.insertWorkouts(response);
                            hasError = false;
                        })
                        .onErrorReturn(throwable -> {
                            hasError = true;
                            return mStorage.getWeekWorkout();
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                workoutResponse -> getViewState().saveWorkouts(workoutResponse.getWorkouts()),
                                throwable -> getViewState().showError(throwable)
                        )
        );
        if(hasError) getViewState().showNetworkError();
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
                        throwable -> getViewState().showNetworkError()
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
                                throwable -> getViewState().showNetworkError()
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
                        throwable -> getViewState().showNetworkError()
                )
        );
    }

}
