package com.example.traininglog.ui.base.analysis;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.utils.ApiUtils;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class AnalysisPresenter extends BasePresenter<AnalysisView> {
    public void getAnalysisForTypes() {
        mCompositeDisposable.add(
        ApiUtils.getApiService().getCountForTypes(ApiUtils.user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable ->  getViewState().showRefresh())
                .doFinally(this::getAnalysisForMonths)
                .subscribe(
                        typesResponse ->  getViewState().showAnalysisForTypes(typesResponse, -1),
                        getViewState()::showError
                )
        );
    }

    public void getAnalysisForMonths() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        mCompositeDisposable.add(
                ApiUtils.getApiService().getCountForMonth(ApiUtils.user_id, year)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                getViewState()::showAnalysisForMonths,
                                getViewState()::showError
                        )
        );
    }

    public void getAnalysisForMonthsForTypes(int month) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getCountForTypesInMonth(ApiUtils.user_id, month+1)
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
