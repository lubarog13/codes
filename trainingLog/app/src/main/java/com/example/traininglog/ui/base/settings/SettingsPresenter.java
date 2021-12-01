package com.example.traininglog.ui.base.settings;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SettingsPresenter extends BasePresenter<SettingsView> {
    public void updateUser(AuthUser authUser) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().updateUser(authUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        getViewState()::showUser,
                        getViewState()::showError
                )
        );
    }

    public void clearTables(Storage storage) {
        mCompositeDisposable.add(
                Observable.just(storage)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        Storage::clearTables
                )
        );
    }
}
