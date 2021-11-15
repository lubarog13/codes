package com.example.traininglog.ui.base.profile.clubs.all_clubs;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class AllClubsPresenter extends BasePresenter<AllClubsView> {

    public void getClubs() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getClubs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        clubs -> getViewState().showClubs(clubs),
                        throwable -> getViewState().showError(throwable)
                )
        );
    }

    public void getCoaches() {
                mCompositeDisposable.add(
                        ApiUtils.getApiService().getCoaches()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe(disposable -> getViewState().showRefresh())
                                .doFinally(getViewState()::hideRefresh)
                                .subscribe(
                                        coaches -> getViewState().showCoaches(coaches),
                                        throwable -> getViewState().showError(throwable)
                                )
                );
    }

    public void getClubsForCoach(int coach_id) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getClubsForCoach(coach_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                clubResponse -> getViewState().showClubs(clubResponse.getClubs()),
                                throwable -> getViewState().showError(throwable)
                        )
        );
    }
}
