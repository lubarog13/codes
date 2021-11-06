package com.example.traininglog.ui.auth;

import android.content.SharedPreferences;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.data.model.User;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;

public interface AuthView extends BaseView {
    void showSuccess( AuthUser user);
    void navigateHome();
}
