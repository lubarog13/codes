package com.elegion.test.behancer.ui.profile;

import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.utils.ApiUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter {
    private ProfileView mView;
    @Inject
    Storage mStorage;
    @Inject
    BehanceApi mApi;
    private String mUsername;
    @Inject
    public ProfilePresenter() {
    }


    public void setView(ProfileView mView) {
        this.mView = mView;
    }


    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public void getProfile() {
        mCompositeDisposable.add( mApi.getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(mStorage::insertUser)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mView.showRefresh())
                .doFinally(mView::hideRefresh)
                .subscribe(
                        response -> {
                            mView.showUser(response.getUser());
                        },
                        throwable -> {
                            mView.showError();
                        }));
    }
}
