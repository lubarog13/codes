package com.elegion.test.behancer.ui.profile;

import android.util.Log;
import android.view.View;

import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter<ProfileView> {
    private ProfileView mView;
    private Storage mStorage;
    private String mUsername;

    public ProfilePresenter(ProfileView mView, Storage mStorage, String mUsername) {
        this.mView = mView;
        this.mStorage = mStorage;
        this.mUsername = mUsername;
    }
    public void getProfile() {
        Log.e("gP", this.toString());
        mCompositeDisposable.add( ApiUtils.getApiService().getUserInfo(mUsername)
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

    @Override
    public String toString() {
        return "ProfilePresenter{" +
                "mView=" + mView +
                ", mStorage=" + mStorage +
                ", mUsername='" + mUsername + '\'' +
                '}';
    }

    public void setView(ProfileView mView) {
        this.mView = mView;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public void setStorage(Storage mStorage) {
        this.mStorage = mStorage;
    }
}
