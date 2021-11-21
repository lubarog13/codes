package com.example.traininglog.ui.base.profile.coaches;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.Storage;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class CoachPresenter extends BasePresenter<CoachView> {

    private final Storage mStorage;

    public CoachPresenter(Storage mStorage) {
        this.mStorage = mStorage;
    }

    public void getCoaches() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getCoaches()
                .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertCoaches)
                        .onErrorReturn(throwable -> mStorage.getCoaches())
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
