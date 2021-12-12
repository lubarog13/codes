package com.example.traininglog.ui.base.profile.clubs.coach;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.model.Building;
import com.example.traininglog.data.model.ClubCreate;
import com.example.traininglog.utils.ApiUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ClubCreatePresenter extends BasePresenter<ClubCreateView> {
    public void getBuildings() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getBuildings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        getViewState()::showBuildings,
                        getViewState()::showError
                )
        );
    }

    public void createClub(ClubCreate clubCreate) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().createClub(clubCreate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getViewState()::setOk,
                                getViewState()::showError
                        )
        );
    }
}
