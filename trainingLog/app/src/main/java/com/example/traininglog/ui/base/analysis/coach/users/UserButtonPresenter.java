package com.example.traininglog.ui.base.analysis.coach.users;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class UserButtonPresenter extends BasePresenter<UserButtonView> {
    public void getClubs() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getClubsForCoach(ApiUtils.coach_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable ->  getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        clubResponse -> getViewState().showClubs(clubResponse.getClubs()),
                        getViewState()::showError
                )
        );
    }

    public void getUsersForClub(int club_id) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getUsersForClub(club_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable ->  getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                userResponse -> getViewState().showUsers(userResponse.getUsers()),
                                getViewState()::showError
                        )
        );
    }
}
