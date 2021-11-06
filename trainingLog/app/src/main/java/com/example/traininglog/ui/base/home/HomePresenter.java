package com.example.traininglog.ui.base.home;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class HomePresenter extends BasePresenter<HomeView> {

    public void getWorkouts() {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getWeekWorkouts(ApiUtils.user_id)
                .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                workoutResponse -> getViewState().saveWorkouts(workoutResponse.getWorkouts()),
                                throwable -> getViewState().showError(throwable)
                        )
        );
    }

}
