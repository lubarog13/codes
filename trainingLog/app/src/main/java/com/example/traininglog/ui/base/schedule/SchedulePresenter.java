package com.example.traininglog.ui.base.schedule;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.WorkoutForEdit;
import com.example.traininglog.utils.ApiUtils;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SchedulePresenter extends BasePresenter<ScheduleView> {

    private Storage mStorage;
    private boolean hasError = false;

    public void getPresencesForMonth(int month, int year, boolean saveData) {
        if(ApiUtils.coach_id==-1) {
            mCompositeDisposable.add(
                    ApiUtils.getApiService().getPresencesForMonth(ApiUtils.user_id, month, year)
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(response -> {
                                if (saveData)
                                    mStorage.insertMonthPresences(response);
                            })
                            .onErrorReturn(throwable -> {
                                return saveData ? mStorage.getMonthPresences(month) : null;
                            })
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
        } else {
            mCompositeDisposable.add(
                    ApiUtils.getApiService().getWorkoutsForMonth(ApiUtils.coach_id, month, year)
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(response -> {
                                if (saveData)
                                    mStorage.insertWorkouts(response);
                            })
                            .onErrorReturn(throwable -> {
                                return saveData ? mStorage.getMonthWorkout(month-1) : null;
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(disposable -> getViewState().showRefresh())
                            .doFinally(getViewState()::hideRefresh)
                            .subscribe(
                                    presenceResponse -> {
                                        Log.e("responce", presenceResponse.getWorkouts().toString());
                                        getViewState().showWorkouts(presenceResponse.getWorkouts());
                                    },
                                    throwable -> getViewState().showError(throwable)
                            )
            );
        }
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

    public void updateWorkout(WorkoutForEdit workout) {
        mCompositeDisposable.add(
                ApiUtils.getsApiServiceForEditWithNulls().updateWorkout(workout.getId(), workout)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                getViewState()::updateComplete,
                                getViewState()::showError
                        )
        );
    }

    public void getData() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getHalls()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable ->  getViewState().showRefresh())
                        .subscribe(
                                this::getCoaches,
                                getViewState()::showError
                        )
        );
    }

    private void getCoaches(List<Hall> halls) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getCoaches()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .subscribe(
                                coaches -> this.getClubs(halls, coaches),
                                getViewState()::showError
                        )
        );
    }

    private void getClubs(List<Hall> halls, List<Coach> coaches) {
        Coach coach_ = coaches.stream().filter(coach -> coach.getUser().getId()==ApiUtils.user_id).collect(Collectors.toList()).get(0);
        mCompositeDisposable.add(
                ApiUtils.getApiService().getClubsForCoach(coach_.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                clubResponse -> getViewState().showValues(halls, coaches, clubResponse.getClubs()),
                                getViewState()::showError
                        )
        );
    }
}
