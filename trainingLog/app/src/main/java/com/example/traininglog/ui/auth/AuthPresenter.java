package com.example.traininglog.ui.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.api.APIKeyInterceptor;
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.data.model.User;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class AuthPresenter extends BasePresenter<AuthView> {
    Observable<String> login;
    Observable<String> password;

    public AuthPresenter() {
    }

    public void logIn() {
         mCompositeDisposable.add(
            ApiUtils.getApiService().auth(new AuthUser(login.blockingFirst(), password.blockingFirst()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> getViewState().showRefresh())
                    .doFinally(this::getUser)
                .subscribe(
                        getViewState()::showSuccess,
                        throwable -> getViewState().showError(throwable.getMessage())
                )
        );
    }

    private void getUser() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().me()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                responce ->  getViewState().saveUser(responce),
                                throwable -> getViewState().showError(throwable.getMessage())
                        )
        );
    }



}
