package com.elegion.test.behancer.ui.profile;

import com.elegion.data.Storage;
import com.elegion.data.api.BehanceApi;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.utils.ApiUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter {
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

    public void getProfile() {
        mCompositeDisposable.add(mApi.getUserInfo(mProfileView.getUsername())
                .subscribeOn(Schedulers.io())
                .doOnSuccess(mStorage::insertUser)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mProfileView.getUsername()) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mProfileView.showRefresh())
                .doFinally(mProfileView::hideRefresh)
                .subscribe(
                        response -> {
                            mProfileView.showUser(response.getUser());
                        },
                        throwable -> {
                            mProfileView.showError();
                        }));
    }
}
