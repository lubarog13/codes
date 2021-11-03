package com.example.traininglog.common;

import com.arellomobile.mvp.MvpAppCompatFragment;

public abstract class PresenterFragment extends MvpAppCompatFragment {
    protected abstract BasePresenter getPresenter();
}
