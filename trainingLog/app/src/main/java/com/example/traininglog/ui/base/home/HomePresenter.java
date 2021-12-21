package com.example.traininglog.ui.base.home;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.Coach;
import com.example.traininglog.data.model.FCMDevice;
import com.example.traininglog.data.model.Hall;
import com.example.traininglog.data.model.Presence;
import com.example.traininglog.data.model.WorkoutForEdit;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.utils.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class HomePresenter extends BasePresenter<HomeView> {
    private final Storage mStorage;
    private boolean hasError = false;

    public HomePresenter(Storage mStorage) {
        this.mStorage = mStorage;
    }

    public void getWorkouts(boolean save_data) {
        HomeActivity.is_firstOpened = false;
        if(ApiUtils.coach_id==-1) {
            mCompositeDisposable.add(
                    ApiUtils.getApiService().getWeekWorkouts(ApiUtils.user_id)
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(response -> {
                                if (save_data)
                                    mStorage.insertWorkouts(response);
                                hasError = false;
                            })
                            .onErrorReturn(throwable -> {
                                hasError = true;
                                return save_data ? mStorage.getWeekWorkout() : null;
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(disposable -> getViewState().showRefresh())
                            .doFinally(getViewState()::hideRefresh)
                            .subscribe(
                                    workoutResponse -> getViewState().saveWorkouts(workoutResponse.getWorkouts()),
                                    throwable -> getViewState().showError(throwable)
                            )
            );
        }
        else {
            mCompositeDisposable.add(
                    ApiUtils.getApiService().getWorkoutsForCoach(ApiUtils.coach_id)
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(response -> {
                                if (save_data)
                                    mStorage.insertWorkouts(response);
                                hasError = false;
                            })
                            .onErrorReturn(throwable -> {
                                Log.e("throwable", throwable!=null? throwable.getMessage(): "");
                                hasError = true;
                                return save_data ? mStorage.getWeekWorkout() : null;
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(disposable -> getViewState().showRefresh())
                            .doFinally(getViewState()::hideRefresh)
                            .subscribe(
                                    workoutResponse -> getViewState().saveWorkouts(workoutResponse.getWorkouts()),
                                    throwable -> getViewState().showError(throwable)
                            )
            );
        }
        if(hasError) getViewState().showNetworkError();
        hasError = false;
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

    public void createDevice(String token, String name, boolean saveData) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        mCompositeDisposable.add(
                ApiUtils.getApiService().createFCMDevice(new FCMDevice(name, true, format.format(new Date()), token, "android", ApiUtils.user_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(()-> {
                    if(ApiUtils.coach_id!=-1){
                        this.getData();
                    } else {
                        this.getWorkouts(saveData);
                    }
                })
                .subscribe(
                        getViewState()::saveDevice,
                        throwable -> {
                            Log.e("err", throwable.getMessage());
                        }
                )
        );
    }

}
