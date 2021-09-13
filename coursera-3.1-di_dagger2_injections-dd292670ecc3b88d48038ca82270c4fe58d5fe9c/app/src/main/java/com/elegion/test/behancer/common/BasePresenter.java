package com.elegion.test.behancer.common;


import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Vladislav Falzan.
 */

public abstract class BasePresenter{

    protected final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void disposeAll() {
            mCompositeDisposable.dispose();
    }
}
