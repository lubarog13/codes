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
    private String passwordFL;
    private SharedPreferences sp;

    public AuthPresenter() {
    }

    public void logIn() {
        if(sp.contains("id")){
            passwordFL = sp.getString("password", null);
            ApiUtils.user_id=sp.getInt("id", 0);
            ApiUtils.coach_id=sp.getInt("coach_id", -1);
            mCompositeDisposable.add(
                    ApiUtils.getApiService().auth(new AuthUser(sp.getString("username", null), passwordFL))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(disposable -> getViewState().showRefresh())
                            .doFinally(getViewState()::hideRefresh)
                            .subscribe(
                                    getViewState()::showSuccess,
                                    throwable -> getViewState().showLogError(throwable)
                            )
            );
        }
        else {
            passwordFL = password.blockingFirst();
            mCompositeDisposable.add(
                    ApiUtils.getApiService().auth(new AuthUser(login.blockingFirst(), passwordFL))
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
    }

    public void getUser() {
        if(!(ApiUtils.user_id==0)){
            getViewState().navigateHome();
        }
        mCompositeDisposable.add(
                ApiUtils.getApiService().me()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                this::getCoach,
                                throwable -> getViewState().showError(throwable)
                        )
        );
    }

    public void getCoach(User user) {
        if(!user.getIs_coach()){
            saveUser(user, -1);
            return;
        }
        mCompositeDisposable.add(
                ApiUtils.getApiService().getCoach(user.getId())
                .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                coachResponse -> saveUser(user, coachResponse.getCoach().getId()),
                                throwable -> getViewState().showError(throwable)
                        )
        );
    }

    private void saveUser(User user, int coach_id) {
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
        editor.putInt("coach_id", coach_id);
        editor.putString("token", ApiUtils.token);
        editor.putString("password", passwordFL);
        ApiUtils.user_id = user.getId();
        ApiUtils.coach_id = coach_id;
        editor.apply();
        getViewState().navigateHome();
    }
    public void setSharedPreferences(SharedPreferences sp){
        this.sp = sp;
    }


}
