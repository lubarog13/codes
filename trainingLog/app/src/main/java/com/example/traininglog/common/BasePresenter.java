package com.example.traininglog.common;

import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<View extends BaseView>  extends MvpPresenter<View> {
    protected final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void disposeAll() {
        mCompositeDisposable.dispose();
    }

    @Override
    public void onDestroy() {
        disposeAll();
        super.onDestroy();
    }
}
