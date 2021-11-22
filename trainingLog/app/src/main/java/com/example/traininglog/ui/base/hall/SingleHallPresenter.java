package com.example.traininglog.ui.base.hall;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.Storage;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SingleHallPresenter extends BasePresenter<SingleHallView> {
    private final Storage mStorage;

    public SingleHallPresenter(Storage mStorage) {
        this.mStorage = mStorage;
    }

    public void getHall(int hall_id) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getHall(hall_id)
                .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertHall)
                        .onErrorReturn(throwable -> mStorage.getHall(hall_id))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        hall -> getViewState().showHall(hall),
                        getViewState()::showError
                )
        );
    }
}
