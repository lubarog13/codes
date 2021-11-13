package com.elegion.test.behancer.ui.projects;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.utils.ApiUtils;
import com.elegion.test.behancer.data.Storage;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Vladislav Falzan.
 */

@InjectViewState
public class ProjectsPresenter extends BasePresenter<ProjectsView> {

    private final Storage mStorage;
    private boolean haveUser = false;
    private String mUsername = null;

    public ProjectsPresenter(Storage storage) {
        mStorage = storage;
    }

    public ProjectsPresenter(Storage mStorage, boolean haveUser, String mUsername) {
        this.mStorage = mStorage;
        this.haveUser = haveUser;
        this.mUsername = mUsername;
    }

    public void getProjects() {
        if(!haveUser) {
            mCompositeDisposable.add(
                    ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(mStorage::insertProjects)
                            .onErrorReturn(throwable ->
                                    ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(disposable -> getViewState().showRefresh())
                            .doFinally(getViewState()::hideRefresh)
                            .subscribe(
                                    response -> getViewState().showProjects(response.getProjects()),
                                    throwable -> getViewState().showError()
                            )
            );
        }
        else {
            Log.e("user", mUsername);
            mCompositeDisposable.add(
                    ApiUtils.getApiService().getUserProjects(mUsername)
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(mStorage::insertProjects)
                            .onErrorReturn(throwable ->
                                    ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(disposable -> getViewState().showRefresh())
                            .doFinally(getViewState()::hideRefresh)
                            .subscribe(
                                    response -> getViewState().showProjects(response.getProjects()),
                                    throwable -> getViewState().showError()
                            )
            );
        }
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public void openProfileFragment(String username) {
        getViewState().openProfileFragment(username);
    }
}
