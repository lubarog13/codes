package com.example.traininglog.ui.base.profile.clubs;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ClubsPresenter extends BasePresenter<ClubsView> {

    public void getSignUps() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getSignUpsForUser(ApiUtils.user_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable ->  getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        signUpResponse -> getViewState().setSignUps(signUpResponse.getSignUps()),
                        throwable -> getViewState().showError(throwable)
                )

        );
    }

    public void getUsersForClub(int club_id, int signup_id) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getUsersForClub(club_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        userResponse -> getViewState().setUsers(userResponse.getUsers(), signup_id),
                        throwable -> getViewState().showError(throwable)
                )
        );
    }
}
