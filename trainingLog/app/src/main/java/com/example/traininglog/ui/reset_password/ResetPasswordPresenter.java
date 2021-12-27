package com.example.traininglog.ui.reset_password;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ResetPasswordPresenter extends BasePresenter<ResetPasswordView> {
    public void resetPassword(String email) {
        AuthUser authUser = new AuthUser();
        authUser.setEmail(email);
        mCompositeDisposable.add(
                ApiUtils.getApiService().resetPassword(authUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        getViewState()::showSuccess,
                        getViewState()::showError
                )
        );
    }
}
