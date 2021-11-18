package com.example.traininglog.ui.base.schedule;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SchedulePresenter extends BasePresenter<ScheduleView> {

    private Storage mStorage;
    private boolean hasError = false;

    public void getPresencesForMonth(int month, int year) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getPresencesForMonth(ApiUtils.user_id, month, year)
                .subscribeOn(Schedulers.io())
                        .doOnSuccess(response -> {
                            mStorage.insertMonthPresences(response);
                        })
                        .onErrorReturn(throwable ->  mStorage.getMonthPresences(month))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        presenceResponse -> {
                            Log.e("responce", presenceResponse.getPresences().toString());
                            getViewState().showPresences(presenceResponse.getPresences());
                        },
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
                                presenceResponse -> getViewState().showPresence(presenceResponse.getPresences()),
                                throwable -> getViewState().showNetworkError()
                        )
        );
    }

    public void updateReason(String reason, int workout_id) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().updatePresenceForWorkout(ApiUtils.user_id, workout_id, new Presence(null, reason))
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

    public void setStorage(Storage mStorage) {
        this.mStorage = mStorage;
    }
}
