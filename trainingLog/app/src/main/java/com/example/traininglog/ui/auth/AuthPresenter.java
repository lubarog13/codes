package com.example.traininglog.ui.auth;

import android.content.SharedPreferences;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
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
    private SharedPreferences sp;

    public AuthPresenter() {
    }

    public void logIn() {
        if(sp.contains("id")){
            ApiUtils.user_id = sp.getInt("id", 0);
            ApiUtils.token = sp.getString("token", "");
            getViewState().showSuccess(new AuthUser(ApiUtils.token));
            return;
        }
         mCompositeDisposable.add(
            ApiUtils.getApiService().auth(new AuthUser(login.blockingFirst(), password.blockingFirst()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> getViewState().showRefresh())
                    .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        getViewState()::showSuccess,
                        throwable -> getViewState().showError(throwable)
                )
        );
    }

    public void getUser() {
        if(!ApiUtils.token.equals("")){
            getViewState().navigateHome();
        }
        mCompositeDisposable.add(
                ApiUtils.getApiService().me()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                this::saveUser,
                                throwable -> getViewState().showError(throwable)
                        )
        );
    }
    private void saveUser(User user) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", user.getId());
        editor.putString("first_name", user.getFirst_name());
        editor.putString("last_name", user.getLast_name());
        editor.putString("second_name", user.getSecond_name());
        editor.putString("email", user.getEmail());
        editor.putString("sex", user.getSex());
        editor.putString("date_birth", user.getDate_birth().toString());
        editor.putString("username", user.getUsername());
        editor.putBoolean("is_coach", user.getIs_coach());
        editor.putString("token", ApiUtils.token);
        ApiUtils.user_id = user.getId();
        editor.apply();
        getViewState().navigateHome();
    }
    public void setSharedPreferences(SharedPreferences sp){
        this.sp = sp;
    }


}
