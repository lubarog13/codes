package com.example.traininglog.common;

import com.arellomobile.mvp.MvpView;

public interface BaseView extends MvpView {
    void showRefresh();

    void hideRefresh();

    void showError(String why);
}
