package com.example.traininglog.ui.base.profile.buildings;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.Storage;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class BuildingsPresenter extends BasePresenter<BuildingsView> {

    private final Storage mStorage;

    public BuildingsPresenter(Storage mStorage) {
        this.mStorage = mStorage;
    }

    public void getBuildings() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getBuildings()
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertBuildings)
                        .onErrorReturn(throwable -> mStorage.getBuildings())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                getViewState()::showBuildings,
                                getViewState()::showError
                        )
        );
    }
}
