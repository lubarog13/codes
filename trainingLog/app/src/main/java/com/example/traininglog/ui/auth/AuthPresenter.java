package com.example.traininglog.ui.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.api.APIKeyInterceptor;
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.data.model.User;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AuthPresenter extends BasePresenter {
    private final AuthView mView;
    private SharedPreferences sp;

    public AuthPresenter(AuthView mView, SharedPreferences sp) {
        this.mView = mView;
        this.sp = sp;
        if(sp.getString("token", null)!=null){
            ApiUtils.token = sp.getString("token", null);
            ApiUtils.user_id = sp.getInt("id", 0);
            this.mView.navigateHome();
        }
    }

    public void logIn() {
         mCompositeDisposable.add(
            ApiUtils.getApiService().auth(mView.getUser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> mView.showRefresh())
                    .doFinally(this::getUser)
                .subscribe(
                        mView::showSuccess,
                        throwable -> mView.showError(throwable.getMessage())
                )
        );
    }

    private void getUser() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().me()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> mView.showRefresh())
                        .doFinally(mView::hideRefresh)
                        .subscribe(
                                responce ->  saveUser(responce),
                                throwable -> mView.showError(throwable.getMessage())
                        )
        );
    }

    private void saveUser(User user) {
        Log.e("user", user.getEmail());
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", user.getId());
        editor.putString("first_name", user.getFirst_name());
        editor.putString("last_name", user.getLast_name());
        editor.putString("second_name", user.getSecond_name());
        editor.putString("email", user.getEmail());
        editor.putString("sex", user.getSex());
        editor.putString("date_birth", user.getDate_birth().toString());
        editor.putString("username", user.getUsername());
        editor.putBoolean("is_coach", user.Is_coach());
        editor.putString("token", ApiUtils.token);
        ApiUtils.user_id = user.getId();
        editor.apply();
        mView.navigateHome();
    }
}
