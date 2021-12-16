package com.example.traininglog.ui.base.profile.clubs.coach;

import com.arellomobile.mvp.InjectViewState;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.data.model.SignUpForCoachCreate;
import com.example.traininglog.data.model.User;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SignupCreatePresenter extends BasePresenter<SignupCreateView> {
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

    public void create(SignUpForCoachCreate signUpForCoachCreate, String username) {
        User user = new User();
        user.setUsername(username);
        mCompositeDisposable.add(
                ApiUtils.getApiService().getUsersByName(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable ->  getViewState().showRefresh())
                        .subscribe(
                                userResponse -> {
                                    signUpForCoachCreate.setUser(userResponse.getUsers().get(0).getId());
                                    createSignUp(signUpForCoachCreate);
                                },
                                throwable -> {
                                    if (throwable.getMessage()==null) getViewState().showError(throwable);
                                    if(throwable.getMessage().contains("404")) getViewState().showUserNotFound();
                                    else getViewState().showError(throwable);
                                }
                        )
        );
    }

    private void createSignUp(SignUpForCoachCreate signUp) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().createCoachSignup(signUp)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getViewState()::setOk,
                                getViewState()::showError
                        )
        );
    }
}
