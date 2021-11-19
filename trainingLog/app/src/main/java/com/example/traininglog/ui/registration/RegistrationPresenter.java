package com.example.traininglog.ui.registration;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class RegistrationPresenter extends BasePresenter<RegistrationView> {
    public void registration(AuthUser user) {
        Log.e("user", user.toString());
        mCompositeDisposable.add(
                ApiUtils.getApiService().registration(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        user1 -> getViewState().showSuccess(),
                        throwable -> getViewState().showError(throwable)
                )
        );
    }
}
