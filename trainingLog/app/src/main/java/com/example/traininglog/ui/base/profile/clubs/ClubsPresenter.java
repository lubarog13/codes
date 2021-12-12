package com.example.traininglog.ui.base.profile.clubs;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.model.SignUpForCreate;
import com.example.traininglog.utils.ApiUtils;

import java.util.Calendar;
import java.util.Date;

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

    public void getClubs() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getClubsForCoach(ApiUtils.coach_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable ->  getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                clubResponse -> getViewState().setClubs(clubResponse.getClubs()),
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

    public void createSignup(String identifier) {
        Calendar cal = Calendar.getInstance();
        Log.e("id", identifier);
        cal.add(Calendar.MONTH, 1);
        SignUpForCreate signUpForCreate = new SignUpForCreate(identifier, ApiUtils.user_id, new Date(), cal.getTime());
        mCompositeDisposable.add(
                ApiUtils.getApiService().createSignUp(signUpForCreate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                signUp -> getViewState().updateSignUps(signUp),
                                throwable -> getViewState().showCreatingError(throwable)
                        )
        );
    }

    public void deleteSignUp(int id) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().deleteSignup(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        getViewState()::showSuccess,
                        getViewState()::showCreatingError
                )
        );
    }
}
