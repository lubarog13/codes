package com.example.traininglog.ui.base.profile;

import android.content.SharedPreferences;

import androidx.navigation.ActionOnlyNavDirections;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {
    private SharedPreferences sp;

    public void logout() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::resetUser,
                        throwable -> {
                            if(throwable.getMessage().contains("401")) {
                                resetUser();
                            }
                            else getViewState().showError(throwable);
                        }
                )
        );
    }

    public void setSp(SharedPreferences sp) {
        this.sp = sp;
    }

    private void resetUser() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
        ApiUtils.token = null;
        ApiUtils.user_id = 0;
        ApiUtils.coach_id = -1;
        getViewState().goToAuth();
    }
}
