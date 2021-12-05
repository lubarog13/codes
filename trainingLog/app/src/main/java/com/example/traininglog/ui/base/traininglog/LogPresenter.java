package com.example.traininglog.ui.base.traininglog;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.utils.ApiUtils;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class LogPresenter extends BasePresenter<LogView> {
    void getWorkoutsForMonth(int month, int year) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getWorkoutsForMonth(ApiUtils.coach_id, month, year)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                response -> getViewState().showWorkouts(response.getWorkouts()),
                                getViewState()::showError
                        )
        );
    }

    void getPresences(Calendar calendar) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getPresencesForDay(ApiUtils.coach_id,
                        calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.YEAR))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        response -> getViewState().showPresences(response.getPresences()),
                        getViewState()::showError
                )
        );
    }
}
