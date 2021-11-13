package com.elegion.test.behancer.ui.profile;

import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.utils.ApiUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter {
    @Inject
    ProfileView mProfileView;
    @Inject
    Storage mStorage;
    @Inject
    BehanceApi mApi;

    @Inject
    public ProfilePresenter(){
    }

    public void setView(ProfileView view) {
        mProfileView = view;
    }

    public void getProfile(@NonNull String mUsername) {
        mCompositeDisposable.add(mApi.getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mProfileView.showRefresh())
                .doFinally(() -> mProfileView.hideRefresh())
                .subscribe(
                        response -> {
                            mProfileView.showUser(response);
                        },
                        throwable -> {
                            mProfileView.showError();
                        }));
    }
}
