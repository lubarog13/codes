package com.example.traininglog.ui.base.profile.halls;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class HallPresenter extends BasePresenter<HallView> {

    public void getHalls(int building_id) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getHallsForBuilding(building_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        hallsResponse -> getViewState().showHalls(hallsResponse.getHalls()),
                        getViewState()::showError
                )
        );
    }
}
