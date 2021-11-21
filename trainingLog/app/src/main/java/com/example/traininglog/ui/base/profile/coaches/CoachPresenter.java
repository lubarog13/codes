package com.example.traininglog.ui.base.profile.coaches;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class CoachPresenter extends BasePresenter<CoachView> {
    public void getCoaches() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getCoaches()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        getViewState()::showCoaches,
                        getViewState()::showError
                )
        );
    }
}
