package com.example.traininglog.common;

import com.arellomobile.mvp.MvpAppCompatActivity;

public abstract class PresenterActivity extends MvpAppCompatActivity {
    protected abstract BasePresenter getPresenter();
}
